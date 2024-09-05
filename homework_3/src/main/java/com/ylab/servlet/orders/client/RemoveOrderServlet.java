package com.ylab.servlet.orders.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.OrderController;
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

import java.io.IOException;

/**
 * Сервлет для получения удаления своего заказа (для клиента)
 * DELETE /carshop/remove_order
 */
@WebServlet("/remove_order")
public class RemoveOrderServlet extends CarShopServlet {

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarFindDto.class);

        try {
            accessService.isClient(req.getSession().getAttribute("role").toString());
            orderController.removeOrder(
                    (int) req.getSession().getAttribute("id"),
                    req.getParameter("brand"),
                    req.getParameter("model")
            );
            createResponse(HttpServletResponse.SC_OK, "Заказ удален!", resp);
        } catch (NullPointerException e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), resp);
        } catch (NotAccessOperationException e) {
            createResponse(HttpServletResponse.SC_FORBIDDEN, e.getMessage(), resp);
        }
    }
}
