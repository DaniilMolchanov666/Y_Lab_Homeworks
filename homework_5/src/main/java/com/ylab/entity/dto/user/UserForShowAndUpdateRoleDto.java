package com.ylab.entity.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO безопасного вывода информации о пользователях для персонала
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class UserForShowAndUpdateRoleDto {

    @NotNull
    private String username;
    @NotNull
    private Role role;
}
