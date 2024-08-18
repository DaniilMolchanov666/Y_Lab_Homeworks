package com.ylab.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */
@Data
@Builder
@ToString(exclude = "id")
@JsonSerialize
@JsonDeserialize
public class Car implements CarShopEntity{

    private Integer id;
    private String brand;
    private String model;
    private String year;
    private String price;
    private String condition;
}
