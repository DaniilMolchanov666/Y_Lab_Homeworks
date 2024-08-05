package com.ylab.service;

import com.ylab.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет пользователями в системе.
 */
public class UserService {

    private List<User> users = new ArrayList<>();

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user Пользователь для добавления.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Возвращает пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем или null, если пользователь не найден.
     */
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Возвращает список всех пользователей в системе.
     *
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}