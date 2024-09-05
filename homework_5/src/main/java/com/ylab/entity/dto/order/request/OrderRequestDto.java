package com.ylab.entity.dto.order.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.entity.dto.car.request.CarRequestDto;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода и создания заказа
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class OrderRequestDto {

    @NotNull
    private UserForShowAndUpdateRoleDto customer;

    @NotNull
    private CarRequestDto car;

    @NotNull
    private String status;
}
