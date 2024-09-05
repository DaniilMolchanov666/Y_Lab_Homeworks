package com.ylab.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс-сущность представляет заказ на покупку автомобиля.
 */
@Entity
@Table(name = "orders", schema = "car_shop_schema")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

    public Order(CarShopUser customer, Car car, String status) {
        this.customer = customer;
        this.car = car;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private CarShopUser customer;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @NotNull
    private String status;
}
