package com.ylab.in;

import com.ylab.controller.AuditController;
import com.ylab.controller.CarController;
import com.ylab.controller.OrderController;
import com.ylab.controller.UsersController;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AccessService;
import com.ylab.service.AuthenticationService;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;

import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Класс обеспечивает консольный интерфейс для взаимодействия с пользователем.
 */
public class ConsoleInterface {

    private final Scanner scanner = new Scanner(in);
    private AuthenticationService authenticationService;
    private AccessService accessService;
    private UserService userService;
    private User currentUser;
    private CarController carController;
    private OrderController orderController;
    private AuditController auditController;
    private UsersController usersController;

    /**
     * Конструктор, вызывающий метод injectAllDependency() для внедрения нужных зависимостей
     */
    public ConsoleInterface() {
        injectAllDependency();
    }

    /**
     * Метод, вызывающийся при создании экземпляра,
     * который инициализирует нужные зависимости
     */
    private void injectAllDependency() {
        this.userService = new UserService();
        this.authenticationService = new AuthenticationService(userService);
        this.accessService = new AccessService();
        CarService carService = new CarService();
        OrderService orderService = new OrderService();
        this.carController = new CarController(carService);
        this.orderController = new OrderController(orderService, carService);
        this.auditController = new AuditController(accessService);
        this.usersController = new UsersController(userService, accessService);
    }

    /**
     * Метод, вызывающий меню для начала работы приложения
     */
    public void startMenu() {
        while (true) {
            String startMenuText = """
                    
                    1. Регистрация
                    2. Вход
                    3. Выход
                    
                    Выберите действие:
                    """;

            String incorrectChoiceMessage = "Неверный выбор. Попробуйте снова!";
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
            startMenu();
        }

        try {
            Role role = Role.values()[Integer.parseInt(roleChoice) - 1];
            User user = new User(username, password, role);
            userService.addUser(user);
            out.println("Пользователь зарегистрирован");
        } catch (NumberFormatException e) {
            out.println("Неверный формат ввода данных!");
            startMenu();
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
            mainMenu();
        } else {
            out.println("Неверное имя пользователя или пароль!");
        }
    }

    /**
     * Метод для отображения главного меню
     */
    private void mainMenu() {
        while (true) {
            String mainMenuText = """
                    
                    1. Управление автомобилями
                    2. Обработка заказов
                    3. Просмотр информации о клиентах и сотрудниках
                    4. Поиск
                    5. Аудит действий
                    6. Выход
                    
                    Выберите действие:
                    """;

            switch (sendMenuAndGetChoice(mainMenuText)) {
                case "1" -> manageCars();
                case "2" -> manageOrders();
                case "3" -> {
                    if (accessService.isAdmin(currentUser)) {
                        usersController.viewUsers(currentUser);
                    }
                }
                case "4" -> filterAndSearchMenu();
                case "5" -> {
                    if (accessService.isManagerOrAdmin(currentUser)) {
                        auditController.viewAuditLog(currentUser);
                    }
                }
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

        while (true) {
            String carsMenuText = """
                    
                    1. Просмотр списка автомобилей
                    2. Добавление нового автомобиля
                    3. Редактирование информации об автомобиле
                    4. Удаление автомобиля
                    5. Назад
                    
                    Выберите действие:
                    """;

            switch (sendMenuAndGetChoice(carsMenuText)) {
                case "1" -> carController.viewCars();
                case "2" -> {
                    if (accessService.isManagerOrAdmin(currentUser)) {
                        carController.addCar();
                    }
                }
                case "3" -> {
                    if (accessService.isManagerOrAdmin(currentUser)) {
                        carController.editCar();
                    }
                }
                case "4" -> {
                    if (accessService.isManagerOrAdmin(currentUser)) {
                        carController.removeCar();
                    }
                }
                case "5" -> {
                    return;
                }
                default -> out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Метод для отображения меню управления запросами по заказам
     */
    private void manageOrders() {

        while (true) {
            String ordersMenuText = """
                    
                    1. Просмотр списка заказов
                    2. Создание нового заказа
                    3. Изменение статуса заказа
                    4. Удаление заказа
                    5. Назад
                    
                    Выберите действие:
                    """;

            switch (sendMenuAndGetChoice(ordersMenuText)) {
                case "1" -> orderController.viewOrders();
                case "2" -> orderController.createOrder(currentUser);
                case "3" -> orderController.changeOrderStatus();
                case "4" -> orderController.removeOrder();
                case "5" -> {
                    return;
                }
                default -> out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }

    /**
     * Метод для отображения меню управления запросами по поиску автомобилей и заказов
     */
    private void filterAndSearchMenu() {

        String ordersMenuText = """
                
                1. Поиск автомобилей
                2. Поиск заказов
                3. Назад
                
                 Выберите действие:
                """;

        while (true) {
            switch (sendMenuAndGetChoice(ordersMenuText)) {
                case "1" -> carController.searchCars();
                case "2" -> orderController.searchOrders();
                case "3" -> {
                    return;
                }
                default -> out.println("Неверный выбор. Попробуйте снова!");
            }
        }
    }
}