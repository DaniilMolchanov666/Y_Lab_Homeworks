package com.ylab.service;

import com.ylab.entity.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет автомобилями в автосалоне.
 */
public class CarService {
    private List<Car> cars = new ArrayList<>();

    /**
     * Добавляет новый автомобиль в автосалон.
     *
     * @param car Автомобиль для добавления.
     */
    public void addCar(Car car) {
        cars.add(car);
    }

    /**
     * Возвращает список всех автомобилей в автосалоне.
     *
     * @return Список всех автомобилей.
     */
    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    /**
     * Удаляет автомобиль из автосалона.
     *
     * @param car Автомобиль для удаления.
     */
    public void removeCar(Car car) {
        cars.remove(car);
    }
}