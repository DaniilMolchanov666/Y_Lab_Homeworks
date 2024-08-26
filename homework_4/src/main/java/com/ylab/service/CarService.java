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
        if (carRepository.existsById(car.getId())) {
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
        Car foundedCar = carRepository.findByModelAndBrand(model, brand).orElseThrow(NullPointerException::new);
        carRepository.delete(foundedCar);
        carRepository.save(car);
    }

    public Optional<Car> findByModelAndBrand(String brand, String model) throws NullPointerException {
        return carRepository.findByModelAndBrand(model, brand);
    }
}