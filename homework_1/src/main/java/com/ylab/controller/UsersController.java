package com.ylab.controller;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AuthorizationService;
import com.ylab.service.UserService;

import java.util.List;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
public class UsersController {

    private final UserService userService;

    private final AuthorizationService authorizationService;

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param userService сервис для работы с пользователями
     * @param authorizationService сервис авторизации
     */
    public UsersController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    /**
     * Обработка запросов отображения всех пользователей из базы данных
     */
    public void viewUsers(User currentUser) {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            out.println("У вас нет прав для просмотра информации о пользователях!");
            return;
        }

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            out.println("Список пользователей пуст!");
        } else {
            for (User user : users) {
                out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }
}
