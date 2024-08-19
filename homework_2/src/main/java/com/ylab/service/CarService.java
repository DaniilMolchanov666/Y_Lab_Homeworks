package com.ylab.service;

import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.repository.CarRepository;
import com.ylab.utils.AuditLogger;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

/**
 * Класс управляет автомобилями в автосалоне.
 */
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Метод для проверки валидности цены и года выпуска автомобиля
     * - цена должна быть числом
     * - год должен быть числом, размер не менее 4 символа и входить в промежуток от 1970 до нынешнего года
     *
     * @param car - проверяемый автомобиль
     * @return результат проверки на валидность
     */
    public boolean isValidCarValues(Car car) throws ValidationCarDataException {

        var price = car.getPrice().split("");
        var year = car.getYear().split("");

        int currentYear = Year.now().getValue();
        int minSizeOfYearValue = 4;
        int minYear = 1970;

        boolean isDigitPrice = Arrays.stream(price)
                .allMatch(i -> Character.isDigit(i.charAt(0)));

        boolean isValidYear = Arrays.stream(year).allMatch(i -> Character.isDigit(i.charAt(0)))
                && year.length == minSizeOfYearValue
                && ((Integer.parseInt(car.getYear()) >= minYear
                && Integer.parseInt(car.getYear()) <= currentYear));

        if (isValidYear && isDigitPrice) return true;
        else {
            throw new ValidationCarDataException();
        }
    }

    /**
     * Добавляет новый автомобиль в автосалон.
     *
     * @param car Автомобиль для добавления.
     */
    public boolean addCar(Car car) throws NullPointerException {
        return carRepository.add(car);

    }

    /**
     * Возвращает список всех автомобилей в автосалоне.
     *
     * @return Список всех автомобилей.
     */
    public List<Car> getAllCars() {
        return new ArrayList<>(carRepository.getAll());
    }

    /**
     * Обновляет информацию об автомобиле
     *
     * @return true - автомобиль был обновлен, false - произошла ошибка
     */
    public boolean editCar(Car car) throws NullPointerException {
        return carRepository.edit(car);
    }

    public Car getCarByModelAndBrand(String brand, String model) throws NullPointerException {
        return carRepository.getCarByModelAndBrand(brand, model);
    }

    /**
     * Удаляет автомобиль из автосалона.
     *
     * @param car Автомобиль для удаления.
     */
    public void removeCar(Car car) throws NullPointerException {
        carRepository.remove(car);
    }

    public List<Car> viewCars() {
        if (carRepository.getAll().isEmpty()) {
            return Collections.emptyList();
        }
        return carRepository.getAll();
    }
}