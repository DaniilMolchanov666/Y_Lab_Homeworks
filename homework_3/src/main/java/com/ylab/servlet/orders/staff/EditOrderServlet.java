package com.ylab.servlet.orders.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.OrderController;
import com.ylab.entity.dto.OrderFindDto;
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

import java.io.IOException;

/**
 * Сервлет для обновления статуса заказа (только для персонала)
 * PUT /carshop/admin/edit_order
 */
@WebServlet("/admin/edit_order")
public class EditOrderServlet extends CarShopServlet {

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var order = objectMapper.readValue(getJson(req.getReader()), OrderFindDto.class);

        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            orderController.changeOrderStatus(order.getStatus(), order.getBrand(), order.getModel());

            createResponse(HttpServletResponse.SC_OK, "Заказ обновлен!", resp);
        } catch (NotAccessOperationException e2) {
            createResponse(HttpServletResponse.SC_FORBIDDEN, e2.getMessage(), resp);
        } catch (Exception e) {
            createResponse(HttpServletResponse.SC_CONFLICT, "Заказ не был обновлен!\n" + e.getMessage(), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = """
                Введите данные для обновления заказа:
                - марка (брэнд)
                - модель
                - новый статус
                """;
        resp.getWriter().println(message);
    }
}
