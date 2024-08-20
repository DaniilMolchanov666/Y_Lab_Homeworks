package com.ylab.controller;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.User;
import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.OrderDto;
import com.ylab.entity.dto.UserDto;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.mapper.CarMapper;
import com.ylab.mapper.UserMapper;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.utils.AuditLogger;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
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

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final CarMapper carMapper = CarMapper.carMapper;

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
     */
    public void createOrder(User user, String brand, String model) {
        Order order = new Order(
                user,
                carService.getCarByModelAndBrand(brand, model), 
                OrderStatus.CREATED.name()
        );
        orderService.addOrder(order);
    }

    /**
     * Обработка запроса изменения статуса заказа
     */
    public void changeOrderStatus(String status, String brand, String model) {

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                out.print("Введите новый статус заказа: ");

                order.setStatus(status);

                if (orderService.editOrder(order)) {
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
    public List<OrderDto> viewOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderService.viewOrders().forEach(i -> {
            orderDtoList.add(new OrderDto(
                    userMapper.toUserForShowDto(i.getCustomer()),
                    carMapper.toCarDto(i.getCar()),
                    i.getStatus()));
        });
        return orderDtoList;
    }
}
