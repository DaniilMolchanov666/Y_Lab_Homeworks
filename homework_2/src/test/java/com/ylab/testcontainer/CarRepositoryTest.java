package com.ylab.testcontainer;

import com.ylab.entity.Car;
import com.ylab.repository.CarRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class CarRepositoryTest {

    private static final CarRepository carRepository = new CarRepository();

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db_users")
            .withUsername("daniilmolchanov")
            .withPassword("microcuts1928")
            .withInitScript("script_for_tests_cars.sql");


    @BeforeAll
    @DisplayName(value = "Открытие подключения к БД и задание подключения репозиторию")
    public static void setUp() throws SQLException {
        postgresContainer.start();

        var connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        carRepository.setNewConnection(connection);
        var car = Car.builder()
                .model("MAZDA 3")
                .brand("MAZDA")
                .condition("SALE")
                .price("18000000")
                .year("2018")
                .build();
        carRepository.add(car);
    }

    @Test
    @DisplayName(value = "Тест на проверку добавления автомобиля в базу данных")
    public void testAddNewCarInTable() throws SQLException {
        var car = Car.builder()
                .model("BMV")
                .brand("X3")
                .condition("SALE")
                .price("18000000")
                .year("2018")
                .condition("GOOD")
                .build();

        assertTrue(carRepository.add(car));
    }

    @Test
    @DisplayName(value = "Тест на получение списка всех автомобилей")
    public void testGetAllCars() throws SQLException {
        assertEquals(4, carRepository.getAll().size());
    }

    @Test
    @DisplayName(value = "Тест на получение автомобиля по наименованию модели и брэнда")
    public void testFindCar() throws SQLException {
        var car = carRepository.getCarByModelAndBrand("AUDI", "AUDI Q8");
        assertNotNull(car);
    }

    @Test
    @DisplayName(value = "Тест удаления автомобиля")
    public void testRemoveCar() throws SQLException {
        var carRepositorySize = carRepository.getAll();

        carRepository.remove(carRepository.getCarByModelAndBrand("AUDI", "AUDI Q8"));

        var carRepositorySizeAfterRemove = carRepository.getAll();

        assertNotEquals(carRepositorySize, carRepositorySizeAfterRemove);
    }

    @Test
    @DisplayName(value = "Тест на апдейт автомобиля")
    public void testUpdateCar() throws SQLException {

        var car = carRepository.getCarByModelAndBrand("MAZDA", "MAZDA 3");

        var updatedCar = Car.builder()
                .id(car.getId())
                .model("MAZDA 3")
                .brand("MAZDA")
                .condition("SALE")
                .price("10000000")
                .year("2020")
                .condition("")
                .build();

        carRepository.edit(updatedCar);

        assertEquals(carRepository.getCarByModelAndBrand("MAZDA", "MAZDA 3").getPrice(), "10000000");
        assertEquals(carRepository.getCarByModelAndBrand("MAZDA", "MAZDA 3").getYear(), "2020");
        assertEquals(carRepository.getCarByModelAndBrand("MAZDA", "MAZDA 3").getCondition(), "");
    }

    @AfterAll
    @DisplayName(value = "Закрытие подключения к БД после прохождения всех тестов")
    public static void closeConnection() {
        postgresContainer.stop();
        postgresContainer.close();
    }
}