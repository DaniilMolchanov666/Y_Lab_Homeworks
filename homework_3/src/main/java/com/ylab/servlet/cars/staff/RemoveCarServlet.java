package com.ylab.servlet.cars.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.CarService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Сервлет для удаления автомобилей
 * DELETE /remove_car?brand='mazda 3'&model='mazda
 */
@WebServlet("/admin/remove_car")
public class RemoveCarServlet extends CarShopServlet {

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);

        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            carController.removeCar(req.getParameter("brand"), req.getParameter("model"));

            createResponse(HttpServletResponse.SC_OK, "Автомобиль" + car + " удален!", resp);
        } catch (NullPointerException e) {
            createResponse(HttpServletResponse.SC_NOT_FOUND, "Автомобиль не найден!", resp);
        } catch (NotAccessOperationException e) {
            createResponse(HttpServletResponse.SC_CONFLICT, e.getMessage(), resp);
        }
    }
}
