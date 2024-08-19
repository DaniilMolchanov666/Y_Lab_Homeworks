package com.ylab.servlet.cars;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.entity.Car;
import com.ylab.out.LiquibaseConfig;
import com.ylab.repository.CarRepository;
import com.ylab.service.CarService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/show_cars")
public class ShowAllCarsServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private CarController carController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LiquibaseConfig.getConnectionWithLiquiBase();
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> car = objectMapper.readValue(carController.viewCars().toString(), new TypeReference<>() { });
        resp.getWriter().println("Доступные для продажи автомобили: \n" + car);
    }
}
