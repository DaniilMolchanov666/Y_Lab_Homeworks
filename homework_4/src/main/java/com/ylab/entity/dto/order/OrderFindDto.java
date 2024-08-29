package com.ylab.entity.dto.order;

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

    private String brand;

    private String model;

    private String status;
}
