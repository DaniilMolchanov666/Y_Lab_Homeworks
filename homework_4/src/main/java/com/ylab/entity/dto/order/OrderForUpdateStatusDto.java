package com.ylab.entity.dto.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import com.ylab.entity.dto.car.CarForCreateDto;
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
public class OrderForUpdateStatusDto {

    private UserForShowAndUpdateRoleDto customer;

    private CarForCreateDto car;

    private String status;
}
