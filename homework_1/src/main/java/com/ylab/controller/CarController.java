package com.ylab.controller;

import com.ylab.entity.Car;
import com.ylab.service.CarService;
import com.ylab.utils.AuditLogger;

import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с автомобилями
 */
public class CarController {

    private final CarService carService;

    private final Scanner scanner = new Scanner(System.in);

    private final AuditLogger auditLogger = AuditLogger.getInstance();

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
    public void addCar() {
        out.print("Введите марку: ");
        String brand = scanner.nextLine();

        out.print("Введите модель: ");
        String model = scanner.nextLine();

        out.print("Введите год выпуска: ");
        String year = scanner.nextLine();

        out.print("Введите цену: ");
        String price = scanner.nextLine();

        out.print("Введите состояние: ");
        String condition = scanner.nextLine();

        Car car = Car.builder()
                .model(model)
                .brand(brand)
                .condition(condition)
                .price(price)
                .year(year)
                .build();

        if (carService.isValidCarValues(car)) {
            carService.addCar(car);
            auditLogger.logAction("Добавлен новый автомобиль: " + car);
            out.println("Автомобиль добавлен");
        } else {
            out.println("Год и цена должны быть целыми числами! Введите еще раз!");
        }
    }

    /**
     * Обработка запросов на редактирование автомобиля
     */
    public void editCar() {
        out.print("Введите марку автомобиля для редактирования: ");
        String brand = scanner.nextLine();

        out.print("Введите модель автомобиля для редактирования: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                out.print("Введите новый год выпуска: ");
                String year = scanner.nextLine();

                out.print("Введите новую цену: ");
                String price = scanner.nextLine();

                out.print("Введите новое состояние: ");
                String condition = scanner.nextLine();

                Car updatedCar = Car.builder()
                        .model(model)
                        .brand(brand)
                        .condition(condition)
                        .price(price)
                        .year(year)
                        .build();

                if (carService.isValidCarValues(car)) {

                    carService.removeCar(car);
                    carService.addCar(updatedCar);

                    auditLogger.logAction("Отредактирован автомобиль: " + car);

                    out.println("Информация об автомобиле обновлена");
                } else {
                    out.println("Год и цена должны быть целыми числами! Введите еще раз!");
                }
                return;
            }
        }
        out.println("Автомобиль не найден!");
    }

    /**
     * Обработка запросов на удаление автомобиля
     */
    public void removeCar() {
        out.print("Введите марку автомобиля для удаления: ");
        String brand = scanner.nextLine();

        out.print("Введите модель автомобиля для удаления: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                carService.removeCar(car);
                auditLogger.logAction("Удален автомобиль: " + car);

                out.println("Автомобиль удален");
                return;
            }
        }
        out.println("Автомобиль не найден!");
    }
    /**
     * Обработка запросов на просмотр всех автомобилей
     */
    public void viewCars() {
        carService.viewCars();
    }

    /**
     * Обработка запросов поиска автомобиля
     */
    public void searchCars() {
        out.print("Введите марку автомобиля для поиска: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля для поиска: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        boolean found = false;
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                out.println(car);
                found = true;
            }
        }
        if (!found) {
            out.println("Автомобиль не найден!");
        }
    }
}
