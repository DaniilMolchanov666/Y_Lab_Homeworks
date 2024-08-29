package com.ylab.entity.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.entity.Role;
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

    private String username;
    private String password;
    private Role role;
}
