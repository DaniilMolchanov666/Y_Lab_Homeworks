package com.ylab.entity;

import com.ylab.annotation.CarShopPriceValid;
import com.ylab.annotation.CarShopYearValid;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */
@Entity
@Table(name = "cars", schema = "car_shop_schema")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"brand", "model"})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotNull
    @CarShopYearValid
    private String year;

    @NotNull
    @CarShopPriceValid
    private String price;

    @NotEmpty
    private String condition;
}
