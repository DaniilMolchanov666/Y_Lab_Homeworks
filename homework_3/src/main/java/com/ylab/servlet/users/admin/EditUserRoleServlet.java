package com.ylab.servlet.users.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
import com.ylab.entity.Role;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.service.AccessService;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/edit_role")
public class EditUserRoleServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.usersController = new UsersController(new UserService(), new AccessService());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserForShowDto.class);
        try {
            var roleByCurrentUser = req.getSession().getAttribute("role");
            accessService.hasSuitableRole(roleByCurrentUser.toString() , Role.ADMIN);
            usersController.editRoleByUser(user);
            createResponse(HttpServletResponse.SC_CREATED, "Ваш профиль обновлен!" + user, resp);
        } catch (NotAccessOperationException e) {
            createResponse(HttpServletResponse.SC_METHOD_NOT_ALLOWED, e.getMessage(), resp);
        } catch (Exception e) {
            resp.getWriter().println(e.getMessage());
            createResponse(HttpServletResponse.SC_BAD_GATEWAY, "Пользователь не был обновлен!", resp);
        }
    }
}
