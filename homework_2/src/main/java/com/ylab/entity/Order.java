package com.ylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность представляет заказ на покупку автомобиля.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements CarShopEntity{

    public Order(User customer, Car car, String status) {
        this.customer = customer;
        this.car = car;
        this.status = status;
    }

    private Integer id;
    private User customer;
    private Car car;
    private String status;

}
