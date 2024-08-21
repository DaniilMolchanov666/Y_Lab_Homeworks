package com.ylab.controller;

import com.ylab.entity.Order;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.User;
import com.ylab.entity.dto.OrderDto;
import com.ylab.mapper.CarMapper;
import com.ylab.mapper.UserMapper;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами
 */
public class OrderController {

    private final OrderService orderService;

    private final CarService carService;

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
    public void changeOrderStatus(String status, String brand, String model) throws IllegalArgumentException {
        var order = orderService.findOrderByCarName(brand, model);
        OrderStatus.valueOf(status);
        order.setStatus(status);
        orderService.editOrder(order);
    }

    /**
     * Обработка запроса удаления заказа
     */
    public void removeOrder(Integer userId, String brand, String model) {
       var order = orderService.findOrderByIdAndCarName(userId, brand, model);
       orderService.removeOrder(order);
    }


    /**
     * Обработка запроса просмотра заказов
     * @return список заказов
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
