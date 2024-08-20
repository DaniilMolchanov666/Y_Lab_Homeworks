package com.ylab.servlet.cars.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.annotation.Logging;
import com.ylab.controller.CarController;
import com.ylab.repository.CarRepository;
import com.ylab.service.CarService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/show_cars")
public class ShowAllCarsServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private CarController carController;

    @Logging
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String listOfCars = objectMapper.writeValueAsString(carController.viewCars());
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/plain");
        resp.getWriter().println("Доступные для продажи автомобили: \n" + listOfCars);
    }
}
