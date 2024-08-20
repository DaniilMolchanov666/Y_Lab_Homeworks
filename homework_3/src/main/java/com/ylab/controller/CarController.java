package com.ylab.controller;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.mapper.CarMapper;
import com.ylab.service.AccessService;
import com.ylab.service.CarService;
import com.ylab.utils.AuditLogger;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.factory.Mappers;

import java.nio.channels.AcceptPendingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с автомобилями
 */
public class CarController {

    private final CarService carService;

    private final Scanner scanner = new Scanner(System.in);

    private final AuditLogger auditLogger = AuditLogger.getInstance();

    private final CarMapper carMapper = CarMapper.carMapper;


    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param carService сервис для работы с автомобилями
     */
    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Обработка запросов на добавление нового автомобиля в базу данных
     */
    public void addCar(CarDto carDto) throws NullPointerException, ValidationCarDataException {
        var car = carMapper.toCar(carDto);

        carService.isValidCarValues(car);
        carService.addCar(car);

    }

    /**
     * Обработка запросов на редактирование автомобиля
     */
    public void editCar(CarDto carDto) throws SQLException, ValidationCarDataException  {
        var findCar = carService.getCarByModelAndBrand(carDto.getBrand(), carDto.getModel());
        carMapper.update(carDto, findCar);
        carService.isValidCarValues(findCar);
        carService.editCar(findCar);

    }

    /**
     * Обработка запросов на удаление автомобиля
     */
    public void removeCar(String brand, String model) throws SQLException {
        var car = carService.getCarByModelAndBrand(brand, model);
        carService.removeCar(car);
    }

    /**
     * Обработка запросов на просмотр всех автомобилей
     */
    public List<CarDto> viewCars() {
        List<CarDto> carDtoList = new ArrayList<>();
        carService.viewCars().forEach(i -> carDtoList.add(carMapper.toCarDto(i)));
        return carDtoList;
    }

    /**
     * Обработка запросов поиска автомобиля
     */
    public void searchCars(String brand, String model) {
        try {
            var car = carService.getCarByModelAndBrand(brand, model);
            out.println(car);
        } catch (NullPointerException e) {
            out.println("\nАвтомобиль не найден!");
        }
    }
}
