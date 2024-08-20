package com.ylab.servlet.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.controller.OrderController;
import com.ylab.repository.CarRepository;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/show_orders")
public class ShowAllOrdersServlet extends HttpServlet {

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
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        resp.getWriter().println("Оформленные заказы:\n" + listOfCars);
    }
}
