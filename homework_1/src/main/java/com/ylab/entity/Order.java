package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Класс-сущность представляет заказ на покупку автомобиля.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements CarShopEntity{

    private User customer;
    private Car car;
    private String status;

}
