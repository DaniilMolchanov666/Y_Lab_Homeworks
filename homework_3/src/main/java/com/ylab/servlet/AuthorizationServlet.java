package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.exception.NoAuthenticatedException;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Сервлет для авторизации (с сохранением информации и профиле в сессии)
 * POST /carshop/login
 */
@WebServlet("/login")
public class AuthorizationServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.userService = new UserService();
        this.authenticationService = new AuthenticationService(userService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserUpdateDto.class);
        try {
            var foundedUser = authenticationService.authenticate(user.getUsername(), user.getPassword());

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("id", foundedUser.getId());
            httpSession.setAttribute("username", foundedUser.getUsername());
            httpSession.setAttribute("password", foundedUser.getPassword());
            httpSession.setAttribute("role", foundedUser.getRole().name());

            createResponse(HttpServletResponse.SC_OK, "Вы успешно зашли в сиcтему как "
                    + foundedUser.getRole().name() + "!", resp);
        } catch (NoAuthenticatedException e) {
            createResponse(HttpServletResponse.SC_FORBIDDEN, e.getMessage(), resp);
        }
    }
}
