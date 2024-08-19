package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.entity.User;
import com.ylab.exception.NotAuthorizedException;
import com.ylab.out.LiquibaseConfig;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;

public class AuthorizationServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(new UserService());
        LiquibaseConfig.getConnectionWithLiquiBase();
    }

    private String getJson(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "Не удалось прочитать!";
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = objectMapper.readValue(getJson(req.getReader()), User.class);
        try {
            authenticationService.authenticate(user.getUsername(), user.getPassword());

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("username", user.getUsername());
            httpSession.setAttribute("password", user.getPassword());
            httpSession.setAttribute("role", user.getRole().name());

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("Вы успешно зашли в сиcтему как " + user.getRole().name() + "!");
        } catch (NotAuthorizedException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(e.getMessage());
        }
    }
}
