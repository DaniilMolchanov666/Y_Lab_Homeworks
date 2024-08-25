package com.ylab.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ylab.annotaion.ValidPriceCar;
import com.ylab.annotaion.ValidYearCar;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.eclipse.jface.text.templates.GlobalTemplateVariables;

import java.time.Year;

import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */

@Entity
@Table(name = "car_shop_schema.cars")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class Car implements CarShopEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotNull
    @ValidYearCar
    private Date year;

    @NotNull
    @ValidPriceCar
    private String price;

    @OneToOne(mappedBy = "car")
    private Order orders;

    @NotEmpty
    private String condition;
}
