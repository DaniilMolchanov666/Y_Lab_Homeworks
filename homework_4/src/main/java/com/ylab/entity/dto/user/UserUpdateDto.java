package com.ylab.entity.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для обновления и авторизации пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String username;

    private String password;
}
