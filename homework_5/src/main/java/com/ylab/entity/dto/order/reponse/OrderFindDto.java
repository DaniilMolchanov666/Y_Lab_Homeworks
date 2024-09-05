package com.ylab.entity.dto.order.reponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для обновления статуса заказа, также для поиска
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFindDto {

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @NotNull
    private String status;
}
