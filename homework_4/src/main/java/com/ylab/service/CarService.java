package com.ylab.service;

import com.ylab.entity.Car;
import com.ylab.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Класс управляет автомобилями в автосалоне.
 */
@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void addCar(Car car) {
        if (carRepository.existsByBrandAndModel(car.getBrand(), car.getModel())) {
            throw new IllegalArgumentException("Автомобиль с такими данными уже существует!");
        }
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    public void editCar(String brand, String model, Car car) {
        Car foundedCar = carRepository.findByModelAndBrand(model, brand).orElseThrow(() ->
                new NullPointerException("Не существует автомобиля такого брэнда и модели!"));
        carRepository.delete(foundedCar);
        carRepository.save(car);
    }

    public Optional<Car> findByModelAndBrand(String brand, String model) {
        return carRepository.findByModelAndBrand(model, brand);
    }
}