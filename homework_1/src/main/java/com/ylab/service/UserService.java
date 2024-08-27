package com.ylab.service;

import com.ylab.entity.User;
import com.ylab.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет пользователями в системе.
 */
public class UserService {

    private final UserRepository userRepository = new UserRepository();

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user Пользователь для добавления.
     */
    public void addUser(User user) {
        userRepository.add(user);
    }

    /**
     * Возвращает пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем или null, если пользователь не найден.
     */
    public User getUserByUsername(String username) {
        for (User user : userRepository.getAll()) {
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
        return new ArrayList<>(userRepository.getAll());
    }

}