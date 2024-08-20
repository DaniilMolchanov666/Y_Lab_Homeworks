package com.ylab.servlet.cars.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.controller.CarController;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.NotAccessOperationException;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.repository.CarRepository;
import com.ylab.service.AccessService;
import com.ylab.service.AuthenticationService;
import com.ylab.service.CarService;
import com.ylab.service.UserService;
import com.ylab.servlet.CarShopServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import static java.lang.System.out;

@WebServlet("/admin/edit_car")
public class EditCarServlet extends HttpServlet implements CarShopServlet {
    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private CarController carController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.accessService = new AccessService();
        this.authenticationService = new AuthenticationService(new UserService());
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);

        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            carController.editCar(car);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println("\nАвтомобиль " + car + " Обновлен!");
        } catch (ValidationCarDataException e1) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println(e1.getMessage());
        } catch (NotAccessOperationException e2) {
            resp.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            resp.getWriter().println(e2.getMessage());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            resp.getWriter().println("\nАвтомобиль не был обновлен!");
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
