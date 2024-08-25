package com.ylab.service;

import com.ylab.entity.Car;
import com.ylab.mapper.CarMapper;
import com.ylab.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Класс управляет автомобилями в автосалоне.
 */
@Repository
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    public void editCar(String brand, String model, Car car) {
        Car foundedCar = carRepository.findByModelAndBrand(model, brand).orElse(null);
        carRepository.delete(foundedCar);
        carRepository.save(car);
    }

    public Optional<Car> findByModelAndBrand(String brand, String model) {
        return carRepository.findByModelAndBrand(model, brand);
    }
}