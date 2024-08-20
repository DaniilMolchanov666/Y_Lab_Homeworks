package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Класс-сущность представляет пользователя системы.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements CarShopEntity{

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private Integer id;
    private String username;
    private String password;
    private Role role;
}

