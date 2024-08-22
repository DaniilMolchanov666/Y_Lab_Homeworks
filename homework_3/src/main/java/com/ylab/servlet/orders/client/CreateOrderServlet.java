package com.ylab.servlet.orders.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.OrderController;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.entity.dto.CarFindDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Сервлет для создания заказа (для клиента)
 * POSET /carshop/create_car
 */
@WebServlet("/create_order")
public class CreateOrderServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private AccessService accessService;

    private OrderController orderController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        var orderService = new OrderService();
        var carService = new CarService(new CarRepository());
        this.orderController = new OrderController(orderService, carService);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarFindDto.class);
        try {
            HttpSession httpSession = req.getSession();
            accessService.isClient(httpSession.getAttribute("role").toString());

            var currentUser = new User(
                    (int) httpSession.getAttribute("id"),
                    httpSession.getAttribute("username").toString(),
                    httpSession.getAttribute("password").toString(),
                    Role.valueOf(httpSession.getAttribute("role").toString())
            );
            orderController.createOrder(currentUser, car.getBrand(), car.getModel());
            createResponse(HttpServletResponse.SC_CREATED, "Заказ " + car + " создан!", resp);
        } catch (NotAccessOperationException e2) {
            createResponse(HttpServletResponse.SC_CONFLICT, e2.getMessage(), resp);
        } catch (Exception e) {
            resp.getWriter().println(e.getMessage());
            createResponse(HttpServletResponse.SC_NOT_FOUND, "Заказ не был добавлен!", resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = """
                Введите данные для добавления автомобиля в заказ:
                - модель
                - брэнд
                """;
        resp.getWriter().println(message);
    }
}
