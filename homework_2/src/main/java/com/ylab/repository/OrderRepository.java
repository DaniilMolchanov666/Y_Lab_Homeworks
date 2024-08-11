package com.ylab.repository;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.Role;
import com.ylab.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения заказов
 */
public class OrderRepository implements CarShopRepository<Order> {

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO car_shop_schema.orders(user_id, car_id, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getCustomer().getId());
            preparedStatement.setInt(2, order.getCar().getId());
            preparedStatement.setString(3, order.getStatus());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "SELECT * FROM car_shop_schema.orders";
        List<Order> listOfOrders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                listOfOrders.add(new Order(
                        findUser(resultSet.getInt(2)),
                        findCar(resultSet.getInt(3)),
                        resultSet.getString("status")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfOrders;
    }

    private User findUser(Integer id) {
        String sql = "SELECT * FROM car_shop_schema.users WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return new User(resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Car findCar(Integer id) {
        String sql = "SELECT * FROM car_shop_schema.cars WHERE id = " + id;
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
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void remove(Order order) {
        String sql = "DELETE FROM car_shop_schema.orders WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
