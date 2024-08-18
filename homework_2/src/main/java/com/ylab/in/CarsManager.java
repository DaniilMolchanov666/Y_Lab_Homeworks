package com.ylab.in;

import com.ylab.controller.CarController;
import com.ylab.entity.User;
import com.ylab.service.AccessService;

import java.util.Map;
import java.util.Scanner;

import static java.lang.System.out;

public class CarsManager {

    private final CarController carController;

    private final Scanner scanner = new Scanner(System.in);

    private boolean stayInsideCarMenu = true;

    public CarsManager(CarController carController) {
        this.carController = carController;
    }

    public void getCarsOperationsForAdminAndManager() {
        Map<String, ProcessingCasesInterface> mapOfCarsMenuCases = Map.of(
                "1", carController::viewCars,
                "2", carController::addCar,
                "3", carController::editCar,
                "4", carController::removeCar,
                "5", () -> this.stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String carsMenuText = """
                    
                    1. Просмотр списка автомобилей
                    2. Добавление нового автомобиля
                    3. Редактирование информации об автомобиле
                    4. Удаление автомобиля
                    5. Назад
                    
                    Выберите действие:
                    """;
            out.println(carsMenuText);
            mapOfCarsMenuCases.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }

    public void getCarsOperationsForClient() {
        Map<String, ProcessingCasesInterface> mapOfCarsMenuCasesForClient = Map.of(
                "1", carController::viewCars,
                "2", () -> this.stayInsideCarMenu = false
        );
        while (stayInsideCarMenu) {
            String carsMenuText = """
                    
                    1. Просмотр списка автомобилей
                    2. Назад
                    
                    Выберите действие:
                    """;

            out.println(carsMenuText);
            mapOfCarsMenuCasesForClient.getOrDefault(scanner.nextLine(),
                    () -> out.println("Неверный выбор. Попробуйте снова!")).doOperation();
        }
    }
}
