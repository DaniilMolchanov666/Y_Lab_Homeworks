package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.exception.NoAuthenticatedException;
import com.ylab.out.LiquibaseConfig;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(new UserService());
        this.userService = new UserService();
        LiquibaseConfig.getConnectionWithLiquiBase();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), UserUpdateDto.class);
        try {
            authenticationService.authenticate(user.getUsername(), user.getPassword());
            var foundedUser = userService.getUserByUsername(user.getUsername());

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("id", foundedUser.getId());
            httpSession.setAttribute("username", foundedUser.getUsername());
            httpSession.setAttribute("password", foundedUser.getPassword());
            httpSession.setAttribute("role", foundedUser.getRole().name());

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("Вы успешно зашли в сиcтему как " + foundedUser.getRole().name() + "!");
        } catch (NoAuthenticatedException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(e.getMessage());
        }
    }
}
