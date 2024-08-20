package com.ylab.servlet.users.all_users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
import com.ylab.entity.dto.UserUpdateDto;
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

@WebServlet("/edit_profile")
public class EditUserServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.usersController = new UsersController(new UserService(), new AccessService());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserUpdateDto.class);
        try {
            int id  = (int) req.getSession().getAttribute("id");
            usersController.editUserById(id, user);
            createResponse(HttpServletResponse.SC_CREATED, "Ваш профиль обновлен!"
                    + usersController.getUserInfo(user.getUsername()), resp);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println("Пользователь не был обновлен!" + e.getMessage());
        }
    }
}
