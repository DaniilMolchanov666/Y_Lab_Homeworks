package com.ylab.in;

import com.ylab.controller.AuditController;
import com.ylab.controller.CarController;
import com.ylab.controller.OrderController;
import com.ylab.controller.UsersController;
import com.ylab.entity.User;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.AuthenticationService;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Класс обеспечивает консольный интерфейс для взаимодействия с пользователем.
 */
public class ConsoleInterface {

    private User currentUser;
    private AuthManager authManager;

    /**
     * Конструктор, вызывающий метод injectAllDependency() для внедрения нужных зависимостей
     */
    public ConsoleInterface() {
        injectAllDependency();
    }

    /**
     * Метод, вызывающийся при создании экземпляра,
     * который инициализирует нужные зависимости
     */
    private void injectAllDependency() {
        var userService = new UserService();
        var authService  = new AuthenticationService(userService);
        this.authManager = new AuthManager(userService, authService, currentUser);
    }

    /**
     * Метод, вызывающий меню для начала работы приложения
     */
    public void startMenu() {
        authManager.doOperation();
        currentUser = authManager.getUser();

        openMainMenuIfUserIsAuthorization(currentUser);
    }

    private void openMainMenuIfUserIsAuthorization(User currentUser) {
        if (currentUser != null) {
            mainMenu();
        } else {
            startMenu();
        }
    }

    /**
     * Метод для отображения главного меню
     */
    private void mainMenu() {
        var mainManager = new MainManager();
        mainManager.setAuthManager();
    }
}
