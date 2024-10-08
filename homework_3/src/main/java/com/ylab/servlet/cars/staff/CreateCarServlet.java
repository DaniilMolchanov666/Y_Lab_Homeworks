package com.ylab.servlet.cars.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.CarService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Сервлет для создания автомобилей (только для персонала)
 * POST /carshop/admin/create_car
 */
@WebServlet("/admin/create_car")
public class CreateCarServlet extends CarShopServlet {

    private ObjectMapper objectMapper;

    private AccessService accessService;

    private CarController carController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);
        try {
            HttpSession httpSession = req.getSession();
            accessService.isManagerOrAdmin(httpSession.getAttribute("role").toString());
            carController.addCar(car);
            createResponse(HttpServletResponse.SC_CREATED, "\nАвтомобиль " + car.toString() + " добавлен!", resp);
        } catch (ValidationCarDataException e1) {
            createResponse(HttpServletResponse.SC_CONFLICT, e1.getMessage(), resp);
        } catch (NotAccessOperationException e2) {
            createResponse(HttpServletResponse.SC_CONFLICT, e2.getMessage(), resp);
        } catch (Exception e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, "Автомобиль не был добавлен!", resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = """
                Введите данные для автомобиля:
                - марка (брэнд)
                - модель
                - цена (целое число)
                - год выпуска (от 1970 до нынешнего года)
                - состояние
                """;
        resp.getWriter().println(message);
    }
}
