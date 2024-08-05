package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;

/**
 * Класс представляет автомобиль в автосалоне.
 */

@Data
@AllArgsConstructor
public class Car {

    private String brand;
    private String model;
    private String year;
    private String price;
    private String condition;


}
