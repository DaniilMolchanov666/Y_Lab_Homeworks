package com.ylab.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ylab.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO безопасного вывода информации о пользователях для персонала
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
@JsonDeserialize
public class UserForShowDto {

    @JsonProperty
    private String username;
    @JsonProperty
    private Role role;
}
