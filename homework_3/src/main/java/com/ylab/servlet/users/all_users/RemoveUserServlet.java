package com.ylab.servlet.users.all_users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для удаления профиля пользователем (для всех)
 * DELETE /carshop/remove_profile
 */
@WebServlet("/remove_profile")
public class RemoveUserServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.usersController = new UsersController(new UserService());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            usersController.removeUser((int) req.getSession().getAttribute("id"));
            createResponse(HttpServletResponse.SC_OK, "Ваш профиль удален!", resp);
        } catch (Exception e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, "Пользователь не был удален!", resp);
        }
    }
}
