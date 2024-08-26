package com.ylab.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ylab.annotaion.ValidPriceCar;
import com.ylab.annotaion.ValidYearCar;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */

@Entity
@Table(name = "cars", schema = "car_shop_schema")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"brand", "model"})
@JsonSerialize
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotNull
    @ValidYearCar
    private String year;

    @NotNull
    @ValidPriceCar
    private String price;

    @NotEmpty
    private String condition;
}
