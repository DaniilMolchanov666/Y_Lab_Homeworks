package com.ylab.service;

import com.ylab.entity.User;
import com.ylab.repository.UserRepository;

import java.util.Collections;
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
        return userRepository.findUserByUserName(username);
    }

    /**
     * Возвращает список всех пользователей в системе
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        List<User> listOfUsers = userRepository.getAll();
        if (listOfUsers.isEmpty()) {
            return Collections.emptyList();
        }
        return listOfUsers;
    }

    /**
     * Редактирование пользователем его же профиля
     * @param user - текущий пользователь
     */
     public void editUser(User user) {
        userRepository.edit(user);
     }

    /**
     * Редактирование администрацией роли пользователя
     * @param user - искомый пользователь
     */
     public void editRoleUser(User user) {
        userRepository.editRole(user);
     }

    /**
     * Получение пользователя по id (извлекается из сессии)
     * @param id - id пользователя
     */
     public User getUserById(Integer id) {
        return userRepository.findUserById(id);
     }

    /**
     * Удаление пользователя по id (извлекается из сессии)
     * @param id - id пользователя
     */
     public void removeById(Integer id) {
        userRepository.removeById(id);
     }
}