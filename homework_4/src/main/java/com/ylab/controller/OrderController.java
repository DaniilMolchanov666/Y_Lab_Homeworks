package com.ylab.controller;

import com.ylab.entity.Order;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.User;
import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.OrderDto;
import com.ylab.entity.dto.OrderFindDto;
import com.ylab.mapper.CarMapper;
import com.ylab.mapper.OrderMapper;
import com.ylab.mapper.UserMapper;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами
 */
@RestController()
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    private final CarService carService;

    private final UserService userService;

//    private final UserMapper userMapper;
//
//    private final CarMapper carMapper;

    private final OrderMapper orderMapper;

    @GetMapping(value = "/show_orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDto>> showCars() {
        List<OrderDto> listOfOrders = orderService.viewOrders().stream()
                .map(orderMapper::toOrderDto)
                .toList();
        return ResponseEntity.ok(listOfOrders);
    }

    /**
     * Обработка запроса создания заказа
     *
     */
    @PostMapping(value = "/create_order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(User user, String brand, String model) {
        Order order = new Order(
                user,
                carService.findByModelAndBrand(brand, model).orElse(null),
                OrderStatus.CREATED.name()
        );
        orderService.addOrder(order);
        return ResponseEntity.ok(order);
    }

    /**
     * Обработка запроса изменения статуса заказа
     */
    @PatchMapping(value = "/admin/edit_order", produces = MediaType.APPLICATION_JSON_VALUE)
    public void changeOrderStatus(@Valid @RequestBody OrderFindDto orderFindDto) throws IllegalArgumentException {
        String brand = orderFindDto.getModel();
        String model = orderFindDto.getBrand();
        var order = orderService.findOrder(model, brand);
        order.setStatus(orderFindDto.getStatus());
        orderService.editOrder(brand, model, order);
    }

//    /**
//     * Обработка запроса удаления заказа
//     */
//    @DeleteMapping(value = "/remove_order", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void removeOrder(@Valid @RequestBody OrderFindDto orderFindDto) {
//        var order = new Order();
//        order.setCar(carService.findByModelAndBrand(orderFindDto.getBrand(), orderFindDto.getModel()).orElse(null));
//        order.setCustomer(userService);
//        var order = orderService.removeOrder();
//        orderService.removeOrder(order);
//    }
}
