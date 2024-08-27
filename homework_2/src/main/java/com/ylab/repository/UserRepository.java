package com.ylab.repository;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения пользователей
 */
@Setter
public class UserRepository extends CarShopRepository<User> {

    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO car_shop_schema.users(username, password, role) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM car_shop_schema.users";
        List<User> listOfUsers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                var user = new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfUsers;
    }

    @Override
    public void remove(User user) {
        String sql = "DELETE FROM car_shop_schema.users WHERE id = ?";
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean edit(User user) {
        String sql = "UPDATE car_shop_schema.users SET username = ?, password = ?, role = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
