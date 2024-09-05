package com.ylab.servlet.users.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.service.AccessService;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для обновления роли пользователя (только для администрации)
 * PUT /carshop/admin/edit_role
 */
@WebServlet("/admin/edit_role")
public class EditUserRoleServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.usersController = new UsersController(new UserService());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserForShowDto.class);
        try {
            var roleByCurrentUser = req.getSession().getAttribute("role");
            accessService.isAdmin(roleByCurrentUser.toString());
            usersController.editRoleByUser(user);
            createResponse(HttpServletResponse.SC_CREATED, "Ваш профиль обновлен!" + user, resp);
        } catch (NotAccessOperationException e) {
            createResponse(HttpServletResponse.SC_FORBIDDEN, e.getMessage(), resp);
        } catch (Exception e) {
            resp.getWriter().println(e.getMessage());
            createResponse(HttpServletResponse.SC_CONFLICT, "Пользователь не был обновлен!", resp);
        }
    }
}
