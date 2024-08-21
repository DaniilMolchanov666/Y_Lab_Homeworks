package com.ylab.servlet.cars.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.repository.CarRepository;
import com.ylab.service.CarService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для получения всех автомобилей
 * GET /carshop/show_cars
 */
@WebServlet("/show_cars")
public class ShowAllCarsServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private CarController carController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listOfCars = objectMapper.writeValueAsString(carController.viewCars());
        resp.setHeader("Content-Type", "text/plain; charset=UTF-8");
        createResponse(HttpServletResponse.SC_OK, "Доступные для продажи автомобили: \n" + listOfCars, resp);
    }
}
