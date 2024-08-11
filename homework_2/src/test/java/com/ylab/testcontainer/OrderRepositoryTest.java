package com.ylab.testcontainer;

import com.ylab.entity.Car;
import com.ylab.repository.CarRepository;
import com.ylab.repository.OrderRepository;
import com.ylab.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

//TODO ДОДЕЛАТЬ ТЕСТЫ ДЛЯ ЗАКАЗОВ
@Testcontainers
@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    private static final OrderRepository orderRepository = new OrderRepository();

    private static final CarRepository carRepository = new CarRepository();

    private static final UserRepository userRepository = new UserRepository();

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db_orders")
            .withUsername("daniilmolchanov")
            .withPassword("microcuts1928")
            .withInitScript("script_for_tests_orders.sql");


    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer.start();

        var connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(), postgresContainer.getPassword());

        carRepository.setNewConnection(connection);
        orderRepository.setNewConnection(connection);
        orderRepository.setNewConnection(connection);
    }

    //
}