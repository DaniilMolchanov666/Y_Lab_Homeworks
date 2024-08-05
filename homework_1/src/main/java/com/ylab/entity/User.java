package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс представляет пользователя системы.
 */

@Data
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private Role role;

}

