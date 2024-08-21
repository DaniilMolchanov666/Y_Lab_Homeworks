package com.ylab.servlet.users.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.UsersController;
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

/**
 * Сервлет для получения списка пользователей (только для персонала)
 * GET /carshop//admin/users
 */
@WebServlet("/admin/users")
public class ShowUsersServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private UsersController usersController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.usersController = new UsersController(new UserService(), accessService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            String listOfUsers = objectMapper.writeValueAsString(usersController.viewUsers());
            createResponse(HttpServletResponse.SC_OK, "Список пользователей: \n" + listOfUsers, resp);
        } catch (NotAccessOperationException e) {
            createResponse(HttpServletResponse.SC_CONFLICT, e.getMessage(), resp);
        }
    }
}