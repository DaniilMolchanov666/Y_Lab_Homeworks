package com.ylab.servlet.cars.staff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ylab.annotation.Logging;
import com.ylab.controller.CarController;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.NotAccessOperationException;
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
import java.sql.SQLException;

/***
 * /remove_car?brand='mazda 3'&model='mazda
 */
@WebServlet("/admin/remove_car")
public class RemoveCarServlet extends HttpServlet implements CarShopServlet {

    private ObjectMapper objectMapper;

    private AuthenticationService authenticationService;

    private CarController carController;

    private AccessService accessService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.authenticationService = new AuthenticationService(new UserService());
        this.accessService = new AccessService();
        this.carController = new CarController(new CarService(new CarRepository()));
    }

    @Logging
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var car = objectMapper.readValue(getJson(req.getReader()), CarDto.class);

        try {
            accessService.isManagerOrAdmin(req.getSession().getAttribute("role").toString());
            carController.removeCar(req.getParameter("brand"), req.getParameter("model"));
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("\nАвтомобиль " + car + " удален!");
        } catch (NullPointerException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("\nАвтомобиль не найден!");
        } catch (NotAccessOperationException e) {
            resp.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
            resp.getWriter().println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
