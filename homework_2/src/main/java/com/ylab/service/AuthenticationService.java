package com.ylab.service;

import com.ylab.entity.User;
import com.ylab.exception.AlreadyRegistrationUserException;
import com.ylab.exception.NotAuthorizedException;

/**
 * Класс обеспечивает аутентификацию пользователей.
 */
public class AuthenticationService {

    private final UserService userService;

    /**
     * Конструктор для создания нового сервиса аутентификации.
     *
     * @param userService Менеджер пользователей.
     */
    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Аутентифицирует пользователя по имени пользователя и паролю.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @return true, если аутентификация прошла успешно, иначе false.
     */
    public void authenticate(String username, String password) throws NotAuthorizedException {
        var user = userService.getUserByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            throw new NotAuthorizedException();
        }
    }

    public void registrationCheck(String username) throws AlreadyRegistrationUserException {
        if (userService.getUserByUsername(username) != null) {
            throw new AlreadyRegistrationUserException();
        }
    }
}
