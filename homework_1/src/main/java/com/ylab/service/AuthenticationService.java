package com.ylab.service;

import com.ylab.entity.User;

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
    public boolean authenticate(String username, String password) {
        User user = userService.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
