package com.ylab.servlet.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.annotation.Logging;
import com.ylab.controller.CarController;
import com.ylab.controller.OrderController;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.exception.NotAuthException;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.AuthenticationService;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/create_order")
public class CreateOrderServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private AccessService accessService;

    private OrderController orderController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(new UserService());
        this.accessService = new AccessService();
        var orderService = new OrderService();
        var carService = new CarService(new CarRepository());
        this.orderController = new OrderController(orderService, carService);
    }

    @Logging
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);
        try {
            HttpSession httpSession = req.getSession();

            authenticationService.authCheck(httpSession.getAttribute("username"));
            accessService.isClient(httpSession.getAttribute("role").toString());

            var currentUser = new User(
                    (int) httpSession.getAttribute("id"),
                    httpSession.getAttribute("username").toString(),
                    httpSession.getAttribute("password").toString(),
                    Role.valueOf(httpSession.getAttribute("role").toString())
            );
            orderController.createOrder(currentUser, car.getBrand(), car.getModel());
            createResponse(HttpServletResponse.SC_CREATED, "\nАвтомобиль " + car + " добавлен!", resp);
        } catch (NotAccessOperationException e2) {
            createResponse(HttpServletResponse.SC_CONFLICT, e2.getMessage(), resp);
        } catch (NotAuthException e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, e.getMessage(), resp);
        } catch (Exception e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, "Автомобиль не был добавлен!", resp);
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
