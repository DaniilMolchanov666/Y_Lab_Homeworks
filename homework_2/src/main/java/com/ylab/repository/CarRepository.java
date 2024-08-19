package com.ylab.repository;

import com.ylab.entity.Car;
import com.ylab.out.LiquibaseConfig;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения автомобилей
 */
@Setter
public class CarRepository extends CarShopRepository<Car> {

    static {
        LiquibaseConfig.getConnectionWithLiquiBase();
    }

    @Override
    public boolean add(Car car) {
        String sql = "INSERT INTO car_shop_schema.cars(brand, model, year, price, condition) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getYear());
            preparedStatement.setString(4, car.getPrice());
            preparedStatement.setString(5, car.getCondition());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        return false;
    }

    @Override
    public List<Car> getAll() {
        String sql = "SELECT * FROM car_shop_schema.cars";
        List<Car> listOfCars = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                var car = Car.builder().id(resultSet.getInt("id"))
                        .model(resultSet.getString("model"))
                        .brand(resultSet.getString("brand"))
                        .year(resultSet.getString("year"))
                        .price(resultSet.getString("price"))
                        .condition(resultSet.getString("condition"))
                        .build();
                listOfCars.add(car);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfCars;
    }

    /**
     * Поиск автомобиля по бренду и модели
     * @param brand, model - поля, по которым идет поиск
     * @return искомый автомобиль
     */
    public Car getCarByModelAndBrand(String brand, String model) {
        String sql = "SELECT * FROM car_shop_schema.cars WHERE brand = '" + brand + "' AND model = '" + model + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return Car.builder().id(resultSet.getInt("id"))
                        .model(resultSet.getString("model"))
                        .brand(resultSet.getString("brand"))
                        .year(resultSet.getString("year"))
                        .price(resultSet.getString("price"))
                        .condition(resultSet.getString("condition"))
                        .build();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void remove(Car car) {
        String sql = "DELETE FROM car_shop_schema.cars WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean edit(Car car) {
        String sql = "UPDATE car_shop_schema.cars SET year = ?, price = ?, condition = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, car.getYear());
            preparedStatement.setString(2, car.getPrice());
            preparedStatement.setString(3, car.getCondition());
            preparedStatement.setInt(4, car.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
