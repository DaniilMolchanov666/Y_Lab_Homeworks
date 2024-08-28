package com.ylab.entity.dto.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylab.annotaion.ValidPriceCar;
import com.ylab.annotaion.ValidYearCar;
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
@JsonDeserialize
public class CarForCreateDto {

    @JsonProperty
    private String brand;

    @JsonProperty
    private String model;

    @JsonProperty
    @ValidYearCar
    private String year;

    @JsonProperty
    @ValidPriceCar
    private String price;

    @JsonProperty
    private String condition;
}
