package com.ylab.in;

import com.ylab.controller.UsersController;
import com.ylab.entity.User;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

public class UserManager {

    private final UsersController userController;

    private final Scanner scanner = new Scanner(System.in);

    private boolean stayInsideCarMenu = true;

    public UserManager(UsersController userController, User user) {
        this.userController = userController;
    }

    public void getCarsOperationsForClient() {
        Map<String, ProcessingCasesInterface> mapOfCarsMenuCases = Map.of(
                "1", userController::getUserInfo,
                "2", userController::editUser,
                "3", userController::removeUser,
                "5", () -> this.stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String carsMenuText = """
                    
                    1. Посмотреть свои данные
                    2. Редактировать свои данные
                    3. Удалить свой аккаунт
                    4. Назад
                    
                    Выберите действие:
                    """;
            out.println(carsMenuText);
            mapOfCarsMenuCases.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    public void getCarsOperationsForAdminAndManager() {
        Map<String, ProcessingCasesInterface> mapOfCarsMenuCasesForClient = Map.of(
                "1", userController::viewUsers,
                "2", () -> this.stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String carsMenuText = """
                    
                    1. Просмотр списка пользователей
                    2. Назад
                    
                    Выберите действие:
                    """;

            out.println(carsMenuText);
            mapOfCarsMenuCasesForClient.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }
}
