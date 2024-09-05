package com.ylab.entity.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода и создания пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class UserForRegisterDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private Role role;
}
