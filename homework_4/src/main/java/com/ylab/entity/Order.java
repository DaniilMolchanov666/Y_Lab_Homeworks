package com.ylab.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Класс-сущность представляет заказ на покупку автомобиля.
 */
@Entity
@Table(name = "car_shop_schema.orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize
public class Order implements CarShopEntity{

    public Order(User customer, Car car, String status) {
        this.customer = customer;
        this.car = car;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    private String status;
}
