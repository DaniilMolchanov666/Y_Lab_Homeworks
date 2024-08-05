package com.ylab.entity;

/**
 * Класс представляет пользователя системы.
 */
public class User {

    private String username;
    private String password;
    private Role role;

    /**
     * Конструктор для создания нового пользователя.
     *
     * @param username Имя пользователя.
     * @param password Пароль пользователя.
     * @param role Роль пользователя.
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}

