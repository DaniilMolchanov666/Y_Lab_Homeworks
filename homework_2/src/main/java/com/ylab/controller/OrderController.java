package com.ylab.controller;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.User;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.utils.AuditLogger;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с заказами
 */
public class OrderController {

    private final OrderService orderService;

    private final CarService carService;

    private final Scanner scanner = new Scanner(System.in);

    private final AuditLogger auditLogger = AuditLogger.getInstance();

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param orderService сервис для работы с заказами
     * @param carService сервис для работы с автомобилями
     */
    public OrderController(OrderService orderService, CarService carService) {
        this.orderService = orderService;
        this.carService = carService;
    }

    /**
     * Обработка запроса создания заказа
     *
     * @param currentUser текущий пользователь данной сессии
     */
    public void createOrder(User currentUser) {
        out.print("Введите марку автомобиля для заказа: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля для заказа: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                Order order = new Order(currentUser, car, "Новый");
                if (orderService.addOrder(order)) {
                    auditLogger.logAction("Создан новый заказ: " + order);
                    out.println("\nЗаказ создан");
                    return;
                } else {
                    out.println("\nЗаказ не создан");
                }
            }
        }
        out.println("\nЗаказ не найден!");
    }

    /**
     * Обработка запроса изменения статуса заказа
     */
    public void changeOrderStatus() {
        out.print("Введите марку автомобиля заказа для изменения статуса: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для изменения статуса: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                out.print("Введите новый статус заказа: ");
                String status = scanner.nextLine();

                order.setStatus(status);

                if (orderService.editOrder(order)) {
                    auditLogger.logAction("Статус заказа обновлен! " + order);
                    out.println("\nЗаказ обновлен!");
                    return;
                } else {
                    out.println("\nЗаказ не обновлен!");
                }
                return;
            }
        }
        out.println("\nЗаказ не найден!");
    }

    /**
     * Обработка запроса удаления заказа
     */
    public void removeOrder() {
        out.print("Введите марку автомобиля заказа для удаления: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для удаления: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                orderService.removeOrder(order);
                auditLogger.logAction("Удален заказ: " + order);
                out.println("\nЗаказ удален!");
                return;
            }
        }
        out.println("\nЗаказ не найден!");
    }

    /**
     * Обработка запроса поиска заказа
     */
    public void searchOrders() {
        out.print("Введите марку автомобиля заказа для поиска: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для поиска: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        boolean found = false;
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                out.println(order);
                found = true;
            }
        }
        if (!found) {
            out.println("\nЗаказ не найден!");
        }
    }

    /**
     * Обработка запроса просмотра заказов
     */
    public void viewOrders() {
        orderService.viewOrders();
    }
}
