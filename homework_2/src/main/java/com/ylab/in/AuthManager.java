package com.ylab.in;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AuthenticationService;
import com.ylab.service.UserService;
import lombok.Getter;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

public class AuthManager {

    @Getter
    private User user;

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final Scanner scanner = new Scanner(System.in);

    private boolean stayIntoLoginMenu = true;

    public AuthManager(UserService userService, AuthenticationService authenticationService, User currentUser) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.user = currentUser;
    }

    private final Map<String, ProcessingCasesInterface> mapOfAuthMenuCases = Map.of(
            "1", this::registerUser,
            "2", this::loginUser,
            "3", this::exitFromApplication
    );


    public void doOperation() {
        while (stayIntoLoginMenu) {
            String startMenuText = """
                
                1. Регистрация
                2. Вход
                3. Выход
                
                Выберите действие:
                """;
            out.println(startMenuText);
            mapOfAuthMenuCases.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    /**
     * Метод для отображения меню регистрации пользователя
     */
    private void registerUser() {
        out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        out.print("Введите пароль: ");
        String password = scanner.nextLine();

        out.print("Выберите роль (1 - ADMIN, 2 - MANAGER, 3 - CLIENT): ");
        String roleChoice = scanner.nextLine();

        if (userService.getUserByUsername(username) != null) {
            out.println("Пользователь с таким именем уже зарегистрирован!");
            return;
        }

        try {
            Role role = Role.values()[Integer.parseInt(roleChoice) - 1];
            User user = new User(username, password, role);
            userService.addUser(user);
            this.user = user;
            out.println("Пользователь зарегистрирован");
        } catch (NumberFormatException e) {
            out.println("Неверный формат ввода данных!");
        }
    }

    /**
     * Метод для отображения меню авторизации пользователя
     */
    private void loginUser() {

        out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (authenticationService.authenticate(username, password)) {
            user = userService.getUserByUsername(username);
            out.println("Вход выполнен успешно!");
            stayIntoLoginMenu = false;
        } else {
            out.println("Неверное имя пользователя или пароль!");
        }
    }

    /**
     * Метод для завершения работы приложения
     */
    private void exitFromApplication() {
        System.exit(0);
    }
}
