package com.ylab.repository;

import com.ylab.entity.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения автомобилей
 */
public class CarRepository implements CarShopRepository<Car> {

    /**
     * Список для хранения автомобилей
     */
    private List<Car> cars = new ArrayList<>();

    @Override
    public void add(Car car) {
        cars.add(car);
    }

    @Override
    public List<Car> getAll() {
        return new ArrayList<>(cars);
    }

    @Override
    public void remove(Car car) {
        cars.remove(car);
    }
}
