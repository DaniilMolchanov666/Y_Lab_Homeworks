package com.ylab.entity.dto.user;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String username;
    @NotNull
    private String password;
}
