package com.ylab.in;

import com.ylab.controller.OrderController;
import com.ylab.entity.User;
import com.ylab.service.AccessService;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

public class OrdersManager {

    private final AccessService accessService;

    private OrderController orderController;

    private User currentUser;

    private final Scanner scanner = new Scanner(System.in);

    private boolean stayInsideOrderMenu = true;

    public OrdersManager(OrderController orderController, AccessService accessService, User currentUser) {
        this.orderController = orderController;
        this.currentUser = currentUser;
        this.accessService = accessService;
    }

    public void getOrdersOperationsForAdminAndManager() {
        Map<String, ProcessingCasesInterface> mapOfOrdersMenuCases = Map.of(
                "1", () -> orderController.viewOrders(),
                "2", () -> orderController.createOrder(currentUser),
                "3", () -> orderController.changeOrderStatus(),
                "4", () -> orderController.removeOrder(),
                "5", () -> this.stayInsideOrderMenu = false
        );
        while (stayInsideOrderMenu) {
            String ordersMenuText = """ 
                    
                    1. Просмотр списка заказов
                    2. Изменение статуса заказа
                    3. Назад
                    
                    Выберите действие:
                    """;

            out.println(ordersMenuText);
            String choice = scanner.nextLine();

            mapOfOrdersMenuCases.getOrDefault(choice,
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    public void getOrdersOperationsForClient() {
            Map<String, ProcessingCasesInterface> mapOfOrdersMenuCasesForClient = Map.of(
                    "1", () -> orderController.viewOrders(),
                    "2", () -> orderController.createOrder(currentUser),
                    "3", () -> orderController.changeOrderStatus(),
                    "4", () -> orderController.removeOrder(),
                    "5", () -> this.stayInsideOrderMenu = false
            );
        while (stayInsideOrderMenu) {
            String ordersMenuText = """ 
                    
                    1. Просмотр списка всех заказов
                    2. Создание нового заказа
                    3. Изменение своего заказа
                    4. Удаление своего заказа
                    5. Назад
                    
                    Выберите действие:
                    """;

            out.println(ordersMenuText);
            String choice = scanner.nextLine();

            mapOfOrdersMenuCasesForClient.getOrDefault(choice,
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }
}

