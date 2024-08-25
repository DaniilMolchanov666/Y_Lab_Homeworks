package com.ylab.service;

import com.ylab.entity.User;
import com.ylab.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Класс управляет пользователями в системе.
 */
@Repository
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Добавляет нового пользователя в систему.
     *
     * @param user Пользователь для добавления.
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * Возвращает пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем или null, если пользователь не найден.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUserName().orElse(new User());
    }

    /**
     * Возвращает список всех пользователей в системе.
     *
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void removeUser(User user) {
        userRepository.delete(user);
    }

}