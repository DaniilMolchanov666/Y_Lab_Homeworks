package com.ylab.service;

import com.ylab.entity.User;
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
    public User authenticate(String username, String password) throws NoAuthenticatedException, NullPointerException {
        var user = userService.getUserByUsername(username);
        if (user == null || !(user.getPassword().equals(password))) {
            throw new NoAuthenticatedException();
        }
        return user;
    }

    /**
     * Проверяет авторизован ли пользователь
     * @param currentUser - пользователь, сохраненный в сессии
     */
    public void authCheck (Object currentUser) throws NotAuthException {
        if (currentUser == null) {
            throw new NotAuthException();
        }
    }

    /**
     * Регистрация пользователя
     * @param username Имя пользователя.
     */
    public void registrationCheck(String username) throws AlreadyRegistrationUserException {
        if (userService.getUserByUsername(username) != null) {
            throw new AlreadyRegistrationUserException();
        }
    }
}
