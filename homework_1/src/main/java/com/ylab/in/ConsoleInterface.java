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
import java.util.Scanner;

/**
 * Класс обеспечивает консольный интерфейс для взаимодействия с пользователем.
 */
public class ConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final ServiceRequestService serviceRequestService;
    private final AuditLogger auditLogger;
    private User currentUser;

    public ConsoleInterface(AuthenticationService authenticationService, AuthorizationService authorizationService,
                            UserService userManager, CarService carManager, OrderService orderManager,
                            ServiceRequestService serviceRequestManager, AuditLogger auditLogger) {
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
        this.userService = userManager;
        this.carService = carManager;
        this.orderService = orderManager;
        this.serviceRequestService = serviceRequestManager;
        this.auditLogger = auditLogger;
    }

    public void start() {
        while (true) {
            System.out.println("1. Регистрация");
            System.out.println("2. Вход");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Выберите роль (1 - ADMIN, 2 - MANAGER, 3 - CLIENT): ");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        Role role = Role.values()[roleChoice - 1];

        User user = new User(username, password, role);
        userService.addUser(user);
        System.out.println("Пользователь зарегистрирован.");
    }

    private void loginUser() {
        System.out.print("Введите имя пользователя: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        if (authenticationService.authenticate(username, password)) {
            currentUser = userService.getUserByUsername(username);
            System.out.println("Вход выполнен успешно.");
            showMainMenu();
        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("1. Управление автомобилями");
            System.out.println("2. Обработка заказов");
            System.out.println("3. Просмотр информации о клиентах и сотрудниках");
            System.out.println("4. Фильтрация и поиск");
            System.out.println("5. Аудит действий");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageCars();
                    break;
                case 2:
                    manageOrders();
                    break;
                case 3:
                    viewUsers();
                    break;
                case 4:
                    filterAndSearch();
                    break;
                case 5:
                    viewAuditLog();
                    break;
                case 6:
                    currentUser = null;
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void manageCars() {
        if (!authorizationService.isAuthorized(currentUser, Role.MANAGER) && !authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            System.out.println("У вас нет прав для управления автомобилями.");
            return;
        }

        while (true) {
            System.out.println("1. Просмотр списка автомобилей");
            System.out.println("2. Добавление нового автомобиля");
            System.out.println("3. Редактирование информации об автомобиле");
            System.out.println("4. Удаление автомобиля");
            System.out.println("5. Назад");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCars();
                    break;
                case 2:
                    addCar();
                    break;
                case 3:
                    editCar();
                    break;
                case 4:
                    removeCar();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewCars() {
        List<Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("Список автомобилей пуст.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    private void addCar() {
        System.out.print("Введите марку: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель: ");
        String model = scanner.nextLine();
        System.out.print("Введите год выпуска: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите цену: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Введите состояние: ");
        String condition = scanner.nextLine();

        Car car = new Car(brand, model, year, price, condition);
        carService.addCar(car);
        auditLogger.logAction("Добавлен новый автомобиль: " + car);
        System.out.println("Автомобиль добавлен.");
    }

    private void editCar() {
        System.out.print("Введите марку автомобиля для редактирования: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля для редактирования: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                System.out.print("Введите новый год выпуска: ");
                int year = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Введите новую цену: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Введите новое состояние: ");
                String condition = scanner.nextLine();

                car = new Car(brand, model, year, price, condition);
                carService.removeCar(car);
                carService.addCar(car);
                auditLogger.logAction("Отредактирован автомобиль: " + car);
                System.out.println("Информация об автомобиле обновлена.");
                return;
            }
        }
        System.out.println("Автомобиль не найден.");
    }

    private void removeCar() {
        System.out.print("Введите марку автомобиля для удаления: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля для удаления: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                carService.removeCar(car);
                auditLogger.logAction("Удален автомобиль: " + car);
                System.out.println("Автомобиль удален.");
                return;
            }
        }
        System.out.println("Автомобиль не найден.");
    }

    private void manageOrders() {
        if (!authorizationService.isAuthorized(currentUser, Role.MANAGER) && !authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            System.out.println("У вас нет прав для управления заказами.");
            return;
        }

        while (true) {
            System.out.println("1. Просмотр списка заказов");
            System.out.println("2. Создание нового заказа");
            System.out.println("3. Изменение статуса заказа");
            System.out.println("4. Удаление заказа");
            System.out.println("5. Назад");
            System.out.print("Выберите действие: ");
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
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Список заказов пуст.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private void createOrder() {
        System.out.print("Введите марку автомобиля для заказа: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля для заказа: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                Order order = new Order(currentUser, car, "Новый");
                orderService.addOrder(order);
                auditLogger.logAction("Создан новый заказ: " + order);
                System.out.println("Заказ создан.");
                return;
            }
        }
        System.out.println("Автомобиль не найден.");
    }

    private void changeOrderStatus() {
        System.out.print("Введите марку автомобиля заказа для изменения статуса: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля заказа для изменения статуса: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                System.out.print("Введите новый статус заказа: ");
                String status = scanner.nextLine();
                order.setStatus(status);
                auditLogger.logAction("Изменен статус заказа: " + order);
                System.out.println("Статус заказа изменен.");
                return;
            }
        }
        System.out.println("Заказ не найден.");
    }

    private void removeOrder() {
        System.out.print("Введите марку автомобиля заказа для удаления: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля заказа для удаления: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                orderService.removeOrder(order);
                auditLogger.logAction("Удален заказ: " + order);
                System.out.println("Заказ удален.");
                return;
            }
        }
        System.out.println("Заказ не найден.");
    }

    private void viewUsers() {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            System.out.println("У вас нет прав для просмотра информации о пользователях.");
            return;
        }

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            for (User user : users) {
                System.out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }

    private void filterAndSearch() {
        System.out.println("1. Поиск автомобилей");
        System.out.println("2. Поиск заказов");
        System.out.print("Выберите действие: ");
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
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void searchCars() {
        System.out.print("Введите марку автомобиля для поиска: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля для поиска: ");
        String model = scanner.nextLine();

        List<Car> cars = carService.getAllCars();
        boolean found = false;
        for (Car car : cars) {
            if (car.getBrand().equals(brand) && car.getModel().equals(model)) {
                System.out.println(car);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Автомобиль не найден.");
        }
    }

    private void searchOrders() {
        System.out.print("Введите марку автомобиля заказа для поиска: ");
        String brand = scanner.nextLine();
        System.out.print("Введите модель автомобиля заказа для поиска: ");
        String model = scanner.nextLine();

        List<Order> orders = orderService.getAllOrders();
        boolean found = false;
        for (Order order : orders) {
            if (order.getCar().getBrand().equals(brand) && order.getCar().getModel().equals(model)) {
                System.out.println(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Заказ не найден.");
        }
    }

    private void viewAuditLog() {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            System.out.println("У вас нет прав для просмотра журнала действий.");
            return;
        }

        List<String> log = auditLogger.getLog();
        if (log.isEmpty()) {
            System.out.println("Журнал действий пуст.");
        } else {
            for (String entry : log) {
                System.out.println(entry);
            }
            auditLogger.exportLogToFile("logs");
        }
    }
}