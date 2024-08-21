package com.ylab.testcontainer;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.repository.CarRepository;
import com.ylab.repository.OrderRepository;
import com.ylab.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryTest {

    private static final OrderRepository orderRepository = new OrderRepository();
    private static final CarRepository carRepository = new CarRepository();
    private static final UserRepository userRepository = new UserRepository();

    private static Order order;

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db_orders")
            .withUsername("daniilmolchanov")
            .withPassword("microcuts1928")
            .withInitScript("script_for_tests_orders.sql");


    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer.start();

        var connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        orderRepository.setNewConnection(connection);

        userRepository.setNewConnection(connection);

        carRepository.setNewConnection(connection);

        var car = Car.builder()
                .id(1)
                .model("MAZDA 6")
                .brand("MAZDA")
                .condition("SALE")
                .price("18000000")
                .year("2018")
                .build();

        carRepository.add(car);

        var user = new User(1, "anton", "5678", Role.CLIENT);

        userRepository.add(user);

        order = new Order(1, user, car, "get ready");

    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName(value = "Тест на проверку добавления нового заказа")
    public void testAddNewOrderInTable() {

        System.out.println(orderRepository.getAll());
        assertTrue(orderRepository.add(order));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName(value = "Тест на проверку отображения всех заказов")
    public void testGetAllOrders() {
        assertNotNull(orderRepository.getAll());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName(value = "Тест удаления заказа")
    public void testRemoveOrder() {

        orderRepository.remove(order);

        assertEquals(0, orderRepository.getAll().size());

    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName(value = "Тест на обновление статуса заказа")
    public void testUpdateOrder() {

        orderRepository.add(order);

        var updatedOrder = orderRepository.getAll().get(0);
        updatedOrder.setStatus("not ready");

        orderRepository.edit(updatedOrder);

        assertEquals("not ready", orderRepository.getAll().get(0).getStatus());
    }

    @AfterAll
    @DisplayName(value = "Закрытие подключения к БД после прохождения всех тестов")
    public static void closeConnection() {
        postgresContainer.stop();
        postgresContainer.close();
    }
}