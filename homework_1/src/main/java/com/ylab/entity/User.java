package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс-сущность представляет пользователя системы.
 */
@Data
@AllArgsConstructor
public class User implements CarShopEntity{

    private String username;
    private String password;
    private Role role;

}

