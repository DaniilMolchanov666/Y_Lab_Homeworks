package com.ylab.entity.dto.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для поиска автомобиля
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarFindDto {

    private String brand;

    private String model;
}
