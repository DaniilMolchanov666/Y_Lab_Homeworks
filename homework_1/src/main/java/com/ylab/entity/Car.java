package com.ylab.entity;

import lombok.*;

import java.util.Objects;

/**
 * Класс-сущность представляет автомобиль в автосалоне.
 */
@Data
@Builder
public class Car implements CarShopEntity{
    private String brand;
    private String model;
    private String year;
    private String price;
    private String condition;


}
