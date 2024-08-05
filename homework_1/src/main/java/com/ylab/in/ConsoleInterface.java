package com.ylab.in;

import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.ServiceRequestService;
import com.ylab.service.UserService;
import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AuthenticationService;
import com.ylab.service.AuthorizationService;
import com.ylab.utils.AuditLogger;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.System.*;

/**
 * Класс обеспечивает консольный интерфейс для взаимодействия с пользователем.
 */
public class ConsoleInterface {

    private Scanner scanner;
    private AuthenticationService authenticationService;
    private AuthorizationService authorizationService;
    private UserService userService;
    private     CarService carService;
    private OrderService orderService;
    private ServiceRequestService serviceRequestService;
    private AuditLogger auditLogger;
    private User currentUser;

    private final String incorrectChoiceMessage = "Неверный выбор. Попробуйте снова!";

    public ConsoleInterface() {
        injectAllDependency();
    }

    /**
     * Метод, вызывающийся при создании экземпляра,
     * загружающий все необходимые для работы приложения зависимости
     */
    private void injectAllDependency() {
        this.scanner = new Scanner(in);
        this.userService = new UserService();
        this.authenticationService = new AuthenticationService(userService);
        this.authorizationService = new AuthorizationService();
        this.carService = new CarService();
        this.orderService = new OrderService();
        this.serviceRequestService = new ServiceRequestService();
        this.auditLogger = new AuditLogger();
    }

    /**
     * Метод для начала работы приложения в консоли
     */
    public void start() {
        while (true) {
            String startMenuText = """
                  
                    1. Регистрация
                    2. Вход
                    3. Выход
                    
                    Выберите действие:
                    """;

            switch (sendMenuAndGetChoice(startMenuText)) {
                case "1" -> registerUser();
                case "2" -> loginUser();
                case "3" -> System.exit(0);
                default -> out.println(incorrectChoiceMessage);
            }
        }
    }

    /**
     * Метод для вывода сообщения пользователю
     * @return вводимое пользователем значение
     */
    public String sendMenuAndGetChoice(String menuText) {
        out.println(menuText);
        return scanner.nextLine();
    }

    /**
     * Метод для отображения меню регистрации пользователя
     */
    private void registerUser() {
        out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        out.print("Введите пароль: ");
        String password = scanner.nextLine();

        out.print("Выберите роль (1 - ADMIN, 2 - MANAGER, 3 - CLIENT): ");
        String roleChoice = scanner.nextLine();

        if (userService.getUserByUsername(username) != null) {
            out.println("Пользователь с таким именем уже зарегистрирован!");
            start();
        }

        try {
            Role role = Role.values()[Integer.parseInt(roleChoice) - 1];
            User user = new User(username, password, role);
            userService.addUser(user);
            out.println("Пользователь зарегистрирован");
        } catch (NumberFormatException e) {
            out.println("Неверный формат ввода данных!");
            start();
        }
    }

    /**
     * Метод для отображения меню авторизации пользователя
     */
    private void loginUser() {

        out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();

        out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (authenticationService.authenticate(username, password)) {
            currentUser = userService.getUserByUsername(username);
            out.println("Вход выполнен успешно!");
            showMainMenu();
        } else {
            out.println("Неверное имя пользователя или пароль!");
        }
    }

    /**
     * Метод для отображения главного меню
     */
    private void showMainMenu() {
        while (true) {

            String mainMenuText = """
                    
                    1. Управление автомобилями
                    2. Обработка заказов
                    3. Просмотр информации о клиентах и сотрудниках
                    4. Фильтрация и поиск
                    5. Аудит действий
                    6. Выход
                    
                    Выберите действие:
                    """;

            switch (sendMenuAndGetChoice(mainMenuText)) {
                case "1" -> manageCars();
                case "2" -> manageOrders();
                case "3" -> viewUsers();
                case "4" -> filterAndSearch();
                case "5" -> viewAuditLog();
                case "6" -> {
                    currentUser = null;
                    return;
                }
                default -> out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Метод для отображения меню для запросов по автомобилям
     */
    private void manageCars() {

        if (Objects.equals(currentUser.getRole(), Role.CLIENT)) {
            out.println("У вас нет прав для управления автомобилями.");
            return;
        }

        while (true) {
            out.println("1. Просмотр списка автомобилей");
            out.println("2. Добавление нового автомобиля");
            out.println("3. Редактирование информации об автомобиле");
            out.println("4. Удаление автомобиля");
            out.println("5. Назад");
            out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewCars();
                case 2 -> addCar();
                case 3 -> editCar();
                case 4 -> removeCar();
                case 5 -> {
                    return;
                }
                default -> out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Метод для отображения всех автомобилей из базы данных
     */
    private void viewCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            out.println("Список автомобилей пуст");
        } else {
            for (Car car : cars) {
                out.println(car);
            }
        }
    }

    /**
     * Метод для добавления нового автомобиля в базу данных
     */
    private void addCar() {
        out.print("Введите марку: ");
        String brand = scanner.nextLine();

        out.print("Введите модель: ");
        String model = scanner.nextLine();

        out.print("Введите год выпуска: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        out.print("Введите цену: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        out.print("Введите состояние: ");
        String condition = scanner.nextLine();

        Car car = new Car(brand, model, year, price, condition);
        carService.addCar(car);
        auditLogger.logAction("Добавлен новый автомобиль: " + car);

        out.println("Автомобиль добавлен");
    }

    /**
     * Метод для редактирования автомобиля
     */
    private void editCar() {
        out.print("Введите марку автомобиля для редактирования: ");
        String brand = scanner.nextLine();

        out.print("Введите модель автомобиля для редактирования: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                out.print("Введите новый год выпуска: ");
                int year = scanner.nextInt();
                scanner.nextLine();

                out.print("Введите новую цену: ");
                double price = scanner.nextDouble();
                scanner.nextLine();

                out.print("Введите новое состояние: ");
                String condition = scanner.nextLine();

                car = new Car(brand, model, year, price, condition);

                carService.removeCar(car);
                carService.addCar(car);
                auditLogger.logAction("Отредактирован автомобиль: " + car);

                out.println("Информация об автомобиле обновлена");
                return;
            }
        }
        out.println("Автомобиль не найден!");
    }

    /**
     * Метод для удаления автомобиля
     */
    private void removeCar() {
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
     * Метод для отображения меню управления запросами по заказам
     */
    private void manageOrders() {
        if (!authorizationService.isAuthorized(currentUser, Role.MANAGER)
                && !authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            out.println("У вас нет прав для управления заказами!");
            return;
        }

        while (true) {
            out.println("1. Просмотр списка заказов");
            out.println("2. Создание нового заказа");
            out.println("3. Изменение статуса заказа");
            out.println("4. Удаление заказа");
            out.println("5. Назад");
            out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewOrders();
                    break;
                case 2:
                    createOrder();
                    break;
                case 3:
                    changeOrderStatus();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    return;
                default:
                    out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Метод для отображения всех заказов
     */
    private void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            out.println("Список заказов пуст");
        } else {
            for (Order order : orders) {
                out.println(order);
            }
        }
    }

    /**
     * Метод для создания заказа
     */
    private void createOrder() {
        out.print("Введите марку автомобиля для заказа: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля для заказа: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                Order order = new Order(currentUser, car, "Новый");
                orderService.addOrder(order);
                auditLogger.logAction("Создан новый заказ: " + order);
                out.println("Заказ создан");
                return;
            }
        }
        out.println("Автомобиль не найден!");
    }

    /**
     * Метод для изменения статуса заказа
     */
    private void changeOrderStatus() {
        out.print("Введите марку автомобиля заказа для изменения статуса: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для изменения статуса: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                out.print("Введите новый статус заказа: ");
                String status = scanner.nextLine();
                order.setStatus(status);
                auditLogger.logAction("Изменен статус заказа: " + order);
                out.println("Статус заказа изменен");
                return;
            }
        }
        out.println("Заказ не найден!");
    }

    /**
     * Метод для удаления заказа
     */
    private void removeOrder() {
        out.print("Введите марку автомобиля заказа для удаления: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для удаления: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                orderService.removeOrder(order);
                auditLogger.logAction("Удален заказ: " + order);
                out.println("Заказ удален!");
                return;
            }
        }
        out.println("Заказ не найден!");
    }

    /**
     * Метод для отображения всех пользователей из базы данных
     */
    private void viewUsers() {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            out.println("У вас нет прав для просмотра информации о пользователях!");
            return;
        }

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            out.println("Список пользователей пуст!");
        } else {
            for (User user : users) {
                out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }

    /**
     * Метод для отображения меню управления запросами по поиску автомобилей и заказов
     */
    private void filterAndSearch() {
        out.println("1. Поиск автомобилей");
        out.println("2. Поиск заказов");
        out.print("Выберите действие: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                searchCars();
                break;
            case 2:
                searchOrders();
                break;
            default:
                out.println("Неверный выбор. Попробуйте снова!");
        }
    }

    /**
     * Метод для поиска автомобиля
     */
    private void searchCars() {
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

    /**
     * Метод для поиска заказа
     */
    private void searchOrders() {
        out.print("Введите марку автомобиля заказа для поиска: ");
        String brand = scanner.nextLine();
        out.print("Введите модель автомобиля заказа для поиска: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        boolean found = false;
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                out.println(order);
                found = true;
            }
        }
        if (!found) {
            out.println("Заказ не найден!");
        }
    }

    /**
     * Метод для выгрузки логов действий пользователей,
     * а также записи всех логов в файл 'logs' в папке resources
     */
    private void viewAuditLog() {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            out.println("У вас нет прав для просмотра журнала действий!");
            return;
        }

        List<String> log = auditLogger.getListOfLogs();
        if (log.isEmpty()) {
            out.println("Журнал действий пуст!");
        } else {
            for (String entry : log) {
                out.println(entry);
            }
            auditLogger.exportLogToFile();
        }
    }
}