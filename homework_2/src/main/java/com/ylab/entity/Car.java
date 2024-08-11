package com.ylab.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */
@Data
@Builder
public class Car implements CarShopEntity{

    private Integer id;
    private String brand;
    private String model;
    private String year;
    private String price;
    private String condition;
}
