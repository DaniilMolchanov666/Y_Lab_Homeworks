package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * Класс представляет заказ на покупку автомобиля.
 */

@Data
@AllArgsConstructor
public class Order {

    private User customer;
    private Car car;
    private String status;

}
