package com.ylab.entity.dto.car.request;

import com.ylab.annotation.CarShopPriceValid;
import com.ylab.annotation.CarShopYearValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для вывода и создания автомобилей
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRequestDto {

    @NotNull
    private String brand;

    @NotNull
    private String model;

    @CarShopYearValid
    private String year;

    @CarShopPriceValid
    private String price;

    @NotEmpty
    private String condition;
}
