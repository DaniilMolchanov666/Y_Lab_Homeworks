package com.ylab.repository;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения заказов
 */
public class OrderRepository extends CarShopRepository<Order> {

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO car_shop_schema.orders(user_id, car_id, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getCustomer().getId());
            preparedStatement.setInt(2, order.getCar().getId());
            preparedStatement.setString(3, order.getStatus());
            preparedStatement.executeUpdate();
        } catch (PSQLException e1) {
            System.out.println("Уже существует заказ с таким автомобилем!");
        } catch (SQLException e2) {
            System.out.println(e2.getMessage());
        }
    }

    @Override
    public List<Order> getAll() {
        String sql = "SELECT * FROM car_shop_schema.orders";
        List<Order> listOfOrders = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            connection.setAutoCommit(false);

            while (resultSet.next()) {
                listOfOrders.add(new Order(
                        resultSet.getInt("id"),
                        findUser(resultSet.getInt("user_id")),
                        findCar(resultSet.getInt("car_id")),
                        resultSet.getString("status")
                ));
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfOrders;
    }

    /**
     * Поиск пользователя по внешнему ключу
     * @param id - id внешнего ключа, ссылающегося на таблицу Users
     * @return искомый пользователь
     */
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
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Поиск автомобиля по внешнему ключу
     * @param id - id внешнего ключа, ссылающегося на таблицу Cars
     * @return искомый автомобиль
     */
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean edit(Order order) {
        String sql = "UPDATE car_shop_schema.orders SET status = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, order.getStatus());
            preparedStatement.setInt(2, order.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Order findOrderByCar(String brand, String model) {
        String sql = "SELECT *\n" +
                "FROM car_shop_schema.orders AS orders\n" +
                "JOIN car_shop_schema.cars AS cars ON orders.car_id = cars.id\n" +
                "WHERE cars.brand = ? AND cars.model = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new Order(
                        findUser(resultSet.getInt("user_id")),
                        findCar(resultSet.getInt("car_id")),
                        resultSet.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Order findCurrentUSerOrder(Integer id, String brand, String model) {
        String sql = "SELECT *\n" +
                "FROM car_shop_schema.orders AS orders\n" +
                "JOIN car_shop_schema.cars AS cars ON orders.car_id = cars.id\n" +
                "WHERE cars.brand = ? AND cars.model = ? AND orders.user_id = ?;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, model);
            preparedStatement.setInt(3, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new Order(
                        findUser(resultSet.getInt("user_id")),
                        findCar(resultSet.getInt("car_id")),
                        resultSet.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Order findOrderByUserWithCarData(Integer userId, String brand, String model) {
            String sql = "SELECT o.*\n" +
                    "FROM car_shop_schema.orders orders\n" +
                    "JOIN car_shop_schema.cars cars ON orders.car_id = cars.id\n" +
                    "JOIN car_shop_schema.users users ON orders.user_id = users.id\n" +
                    "WHERE cars.brand = ? AND cars.model = ? AND users.username = ?;";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, brand);
                preparedStatement.setString(2, model);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    return new Order(
                            findUser(resultSet.getInt("user_id")),
                            findCar(resultSet.getInt("car_id")),
                            resultSet.getString("status")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
    }
}
