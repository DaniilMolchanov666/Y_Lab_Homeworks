package com.ylab.testcontainer;

import com.ylab.entity.Car;
import com.ylab.repository.CarRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class DataBaseTest {


    private final CarRepository carRepository = new CarRepository();

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("daniilmolchanov")
            .withPassword("microcuts1928")
            .withInitScript("script.sql");

    private static Connection connection;

    @BeforeAll
    public static void setUp() throws SQLException {
        postgresContainer.start();
        connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(), postgresContainer.getPassword());
    }

    @BeforeEach
    public void setCon() {
        carRepository.setConnection(connection);
    }

    @Test
    public void testCarsTable() throws SQLException {
        var car = Car.builder()
                .model("MAZDA 3")
                .brand("MAZDA")
                .condition("SALE")
                .price("18000000")
                .year("2018")
                .build();

        assertTrue(carRepository.add(car));
    }
}