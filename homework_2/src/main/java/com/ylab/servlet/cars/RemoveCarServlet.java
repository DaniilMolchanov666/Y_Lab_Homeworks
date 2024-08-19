package com.ylab.servlet.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.entity.dto.CarDto;
import com.ylab.repository.CarRepository;
import com.ylab.service.AuthenticationService;
import com.ylab.service.CarService;
import com.ylab.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

/***
 * /remove_car?brand='mazda 3'&model='mazda
 */
@WebServlet("/remove_car")
public class RemoveCarServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private CarController carController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(new UserService());
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    private String getJson(BufferedReader bufferedReader) {
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "Не удалось прочитать!";
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);

        try {
            String brand = req.getParameter("brand");
            String model = req.getParameter("model");
            carController.removeCar(brand, model);
//            auditLogger.logAction("Обновлен автомобиль: " + car);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("\nАвтомобиль " + car + " удален!");
        } catch (NullPointerException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("\nАвтомобиль не найден!");
        }
    }
}
