package com.ylab.in;

import com.ylab.controller.AuditController;
import com.ylab.entity.User;
import com.ylab.service.AccessService;
import lombok.Setter;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

@Setter
public class MainManager {

    private final Scanner scanner = new Scanner(System.in);
    private CarsManager carsManager;
    private OrdersManager ordersManager;
    private UserManager usersManager;
    private AuthManager authManager;
    private AccessService accessService;
    private User user;
    private AuditController auditController;

    private boolean stayInsideCarMenu = true;

    public void startProcess() {
        if (accessService.isAdmin(user)) {
            getAllOperationsForAdmin();
        } else {
            getAllOperationsForClient();
        }
    }

    //TODO сделать SearchAndFilterManager
    public void getAllOperationsForAdmin() {

        Map<String, ProcessingCasesInterface> mapOfStartMenuCases = Map.of(
                "1", carsManager::getCarsOperationsForAdminAndManager,
                "2", ordersManager::getOrdersOperationsForAdminAndManager,
                "3", usersManager::getCarsOperationsForAdminAndManager,
                "4", out::println,
                "5", () -> auditController.viewAuditLog(user),
                "6", authManager::doOperation
        );
        while (stayInsideCarMenu) {
            String mainMenuText = """
                    
                    1. Управление автомобилями
                    2. Обработка заказов
                    3. Просмотр информации о клиентах и сотрудниках
                    4. Поиск заказов и автомобилей
                    5. Аудит действий
                    6. Выход
                    
                    Выберите действие:
                    """;

            out.println(mainMenuText);
            mapOfStartMenuCases.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    public void getAllOperationsForManager() {
        Map<String, ProcessingCasesInterface> mapOfStartMenuCases = Map.of(
                "1", carsManager::getCarsOperationsForAdminAndManager,
                "2", ordersManager::getOrdersOperationsForAdminAndManager,
//            "3", () -> out.println()
                "4", () -> stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String mainMenuText = """
                    
                    1. Управление автомобилями
                    2. Обработка заказов
                    3. Поиск заказов и автомобилей
                    4. Выход
                    
                    Выберите действие:
                    """;

            out.println(mainMenuText);
            mapOfStartMenuCases.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    public void getAllOperationsForClient() {
        Map<String, ProcessingCasesInterface> mapOfStartMenuCasesForClient = Map.of(
                "1", carsManager::getCarsOperationsForClient,
                "2", usersManager::getCarsOperationsForClient,
//            "3", () -> out.println()
                "4", () -> stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String mainMenuText = """
                    
                    1. Посмотреть автомобилей
                    2. Заказы
                    3. Поиск заказов и автомобилей
                    4. Выход
                    
                    Выберите действие:
                    """;

            out.println(mainMenuText);
            mapOfStartMenuCasesForClient.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }
}
