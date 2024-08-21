package com.ylab.servlet.orders.all;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.OrderController;
import com.ylab.repository.CarRepository;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для получения всех заказов
 * GET /carshop/show_orders
 */
@WebServlet("/show_orders")
public class ShowAllOrdersServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private OrderController orderController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        var orderService = new OrderService();
        var carService = new CarService(new CarRepository());
        this.orderController = new OrderController(orderService, carService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listOfCars = objectMapper.writeValueAsString(orderController.viewOrders());
        createResponse(HttpServletResponse.SC_OK, "Оформленные заказы:\n" + listOfCars, resp);
    }
}
