package com.ylab.service;

import com.ylab.entity.Car;
import com.ylab.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    /**
     * Добавляет нового автомобиля
     * @param car - автомобиль
     */
    public void addCar(Car car) {
        if (carRepository.existsByBrandAndModel(car.getBrand(), car.getModel())) {
            throw new IllegalArgumentException("Автомобиль с такими данными уже существует!");
        }
        carRepository.save(car);
    }

    /**
     * Поиск всех автомобилей
     * @param pageable - объект Pagable для задания параметров выборки значений
     * @return список автомобилей
     */
    public List<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    /**
     * Поиск автомобиля по модели
     * @param model - модель
     * @return автомобиль в виде Optional<Car>
     */
    public Car findCar(String model) {
        return carRepository.findByModel(model).orElseThrow(() ->
                new NullPointerException("Не существует автомобиля такой модели!"));
    }

    /**
     * Удаление автомобиля
     * @param car - автомобиль
     */
    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    /**
     * Обновление автомобиля
     * @param brand - брэнд
     * @param model - модель
     * @param car  - автомобиль, предоставляющий новые данные для обновления
     */
    public void editCar(String brand, String model, Car car) {
        Car foundedCar = carRepository.findByModelAndBrand(model, brand).orElseThrow(() ->
                new NullPointerException("Не существует автомобиля такого брэнда и модели!"));
        carRepository.delete(foundedCar);
        carRepository.save(car);
    }

    /**
     * Поиск автомобиля по модели и брэнду
     * @param brand - брэнд
     * @param model - модель
     * @return автомобиль в виде Optional<Car>
     */
    public Optional<Car> findByModelAndBrand(String brand, String model) {
        return carRepository.findByModelAndBrand(model, brand);
    }
}