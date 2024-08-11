package com.ylab.repository;

import com.ylab.entity.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения автомобилей
 */
public class CarRepository implements CarShopRepository<Car> {

    @Override
    public void add(Car car) {
        String sql = "INSERT INTO car_shop_schema.cars(brand, model, year, price, condition) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getYear());
            preparedStatement.setString(4, car.getPrice());
            preparedStatement.setString(5, car.getCondition());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {
        String sql = "SELECT * FROM car_shop_schema.cars";
        List<Car> listOfCars = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                var car =Car.builder().id(resultSet.getInt("id"))
                        .model(resultSet.getString("model"))
                        .brand(resultSet.getString("brand"))
                        .year(resultSet.getString("year"))
                        .price(resultSet.getString("price"))
                        .condition(resultSet.getString("condition"))
                        .build();
                listOfCars.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfCars;
    }

    @Override
    public void remove(Car car) {
        String sql = "DELETE FROM car_shop_schema.cars WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
