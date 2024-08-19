package com.ylab.controller;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AccessService;
import com.ylab.service.UserService;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
public class UsersController {

    private final UserService userService;

    private final AccessService authorizationService;

    private final User user;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param userService сервис для работы с пользователями
     * @param authorizationService сервис авторизации
     */
    public UsersController(UserService userService, AccessService authorizationService, User user) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.user = user;
    }

    /**
     * Обработка запросов отображения всех пользователей из базы данных
     */
    public void viewUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            out.println("Список пользователей пуст!");
        } else {
            for (User user : users) {
                out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }

    public void getUserInfo() {
        out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
    }

    public void editUser() {
        out.print("Введите новый логин: ");
        String username = scanner.nextLine();

        out.print("Введите новый пароль: ");
        String password = scanner.nextLine();

        out.print("Выберите новую роль (1 - ADMIN, 2 - MANAGER, 3 - CLIENT): ");
        String role = scanner.nextLine();

        userService.editUser(new User(username, password, Role.values()[Integer.parseInt(role) - 1]));
    }

    public void removeUser() {
        userService.remove(user);
        out.println("Ваш аккаунт удален!");
    }
}
