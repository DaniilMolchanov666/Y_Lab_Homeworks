package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.entity.User;
import com.ylab.exception.AlreadyRegistrationUserException;
import com.ylab.out.LiquibaseConfig;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LiquibaseConfig.getConnectionWithLiquiBase();
        this.objectMapper = new ObjectMapper();
        this.userService = new UserService();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(userService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       var user = objectMapper.readValue(getJson(req.getReader()), User.class);
       try {
           authenticationService.registrationCheck(user.getUsername());
           userService.addUser(user);

           resp.setContentType("text/plain");
           resp.setStatus(HttpServletResponse.SC_CREATED);
           resp.getWriter().println("Регистрация успешно пройдена!");
       } catch (AlreadyRegistrationUserException e) {
           resp.getWriter().println(e.getMessage());
       }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Добро пожаловать в наш автосалон! " +
                "\nВведите ваши имя и пароль для регистрации!");
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
}
