package com.ylab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.entity.Order;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.User;
import com.ylab.entity.dto.car.CarFindDto;
import com.ylab.entity.dto.order.OrderFindDto;
import com.ylab.mapper.OrderMapper;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с заказами
 */
@RestController
@RequestMapping("/v1/carshop/")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    private final CarService carService;

    private final OrderMapper orderMapper;

    private final UserService userService;

    private final ObjectMapper objectMapper;

    /**
     * Обработка запроса вывода всех заказов
     *
     */
    @Operation(summary = "Вывод списка заказов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список автомобилей")
    })
    @GetMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> showOrders() {
        List<String> listOfOrders = orderService.viewOrders().stream()
                .map(order -> {
                    try {
                        return objectMapper.writeValueAsString(orderMapper.toOrderDto(order));
                    } catch (JsonProcessingException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Некорректный формат!").getBody();
                    }
                }).toList();
        return ResponseEntity.ok("Список заказов автомобилей:\n" + listOfOrders);
    }

    /**
     * Обработка запроса создания заказа
     * @param carDto - автомобиль для заказа
     * @return - тело ответа
     */
    @Operation(summary = "Создание нового заказа (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ создан!"),
            @ApiResponse(responseCode = "404", description = "Нет пользователя с такими именем и паролем!"),
            @ApiResponse(responseCode = "404", description = "Нет пользователя с такими именем и паролем!")
    })
    @PostMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody CarFindDto carDto) {
        var userDetails = userService.getCurrentAuthenticatedUser();
        int id = userService.getUserByUsername(userDetails.getUsername()).getId();

        var user = new User();
        user.setId(id);
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());

        var car = carService.findByModelAndBrand(carDto.getBrand(), carDto.getModel())
                .orElseThrow(NullPointerException::new);

        Order order = new Order(user, car, OrderStatus.CREATED.name());
        orderService.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body("Заказ " + orderMapper.toOrderDto(order) + "создан!");
    }

    /**
     * Обработка запроса изменения статуса заказа (для персонала)
     * @param orderFindDto - искомый заказ по данным автомобиля
     * @return - тело ответа
     */
    @Operation(summary = "Обновление статуса заказа (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус заказа изменен"),
            @ApiResponse(responseCode = "404", description = "Не существует такого заказа!")
    })
    @PatchMapping(value = "staff/order-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeOrderStatus(@RequestBody OrderFindDto orderFindDto) throws IllegalArgumentException {
        var order = orderService.findOrder(orderFindDto.getModel(), orderFindDto.getBrand());
        order.setStatus(orderFindDto.getStatus());
        orderService.editOrder(order.getId(), order);
        return ResponseEntity.ok("Статус заказа изменен на '%s'".formatted(order.getStatus()));
    }

    /**
     * Обработка запроса удаления заказа
     */
    @Operation(summary = "Удаление заказа (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ удален!"),
            @ApiResponse(responseCode = "404", description = "Не существует такого заказа!")
    })
    @DeleteMapping(value = "staff/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeOrder(@Valid @RequestBody OrderFindDto orderFindDto) {
        orderService.removeOrder(orderService.findOrder(orderFindDto.getModel(), orderFindDto.getBrand()));
        return ResponseEntity.ok("Заказ %s удален!".formatted(orderFindDto));
    }
}
