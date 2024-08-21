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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для обновления автомобилей (только для персонала)
 * PUT /carshop//admin/edit_car
 */
@WebServlet("/admin/edit_car")
public class EditCarServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private CarController carController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);

        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            carController.editCar(car);
            createResponse(HttpServletResponse.SC_CREATED, "\nАвтомобиль " + car + " Обновлен!", resp);
        } catch (ValidationCarDataException e1) {
            createResponse(HttpServletResponse.SC_NOT_MODIFIED, e1.getMessage(), resp);
        } catch (IllegalArgumentException e2) {
            createResponse(HttpServletResponse.SC_NOT_MODIFIED, "Неверный статус!", resp);
        } catch (NotAccessOperationException e3) {
            createResponse(HttpServletResponse.SC_CONFLICT, e3.getMessage(), resp);
        } catch (Exception e4) {
            createResponse(HttpServletResponse.SC_BAD_REQUEST, "Автомобиль не был обновлен!", resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = """
                Введите данные для обновления автомобиля:
                - марка (брэнд)
                - модель
                - цена (целое число)
                - год выпуска (от 1970 до нынешнего года)
                - состояние
                """;
        resp.getWriter().println(message);
    }
}
