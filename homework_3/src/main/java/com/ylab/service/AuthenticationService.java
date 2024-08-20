package com.ylab.service;

import com.ylab.exception.AlreadyRegistrationUserException;
import com.ylab.exception.NoAuthenticatedException;
import com.ylab.exception.NotAuthException;

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
    public void authenticate(String username, String password) throws NoAuthenticatedException, NullPointerException {
        var user = userService.getUserByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            throw new NoAuthenticatedException();
        }
    }

    public void authCheck (Object currentUser) throws NotAuthException {
        if (currentUser == null) {
            throw new NotAuthException();
        }
    }

    public void registrationCheck(String username) throws AlreadyRegistrationUserException {
        if (userService.getUserByUsername(username) != null) {
            throw new AlreadyRegistrationUserException();
        }
    }
}
