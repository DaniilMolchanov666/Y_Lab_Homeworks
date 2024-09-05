package com.ylab.entity.dto.car.response;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String brand;

    @NotNull
    private String model;
}
