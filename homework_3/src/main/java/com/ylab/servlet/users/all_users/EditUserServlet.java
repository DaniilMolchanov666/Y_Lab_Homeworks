package com.ylab.servlet.users.all_users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для обновления данных пользователя (для всех)
 * PUT /carshop/edit_profile
 */
@WebServlet("/edit_profile")
public class EditUserServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.usersController = new UsersController(new UserService());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserUpdateDto.class);
        try {
            int id  = (int) req.getSession().getAttribute("id");
            usersController.editUserById(id, user);
            createResponse(HttpServletResponse.SC_CREATED, "Ваш профиль обновлен!"
                    + usersController.getUserInfo(user.getUsername()), resp);
        } catch (Exception e) {
            createResponse(HttpServletResponse.SC_CONFLICT, "Пользователь не был обновлен!", resp);
        }
    }
}
