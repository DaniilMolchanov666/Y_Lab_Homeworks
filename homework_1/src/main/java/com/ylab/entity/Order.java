package com.ylab.entity;

import java.util.Date;

/**
 * Класс представляет заказ на покупку автомобиля.
 */
public class Order {

    private User customer;
    private Car car;
    private String status;

    /**
     * Конструктор для создания нового заказа.
     *
     * @param customer Клиент, создавший заказ.
     * @param car Автомобиль, на который создан заказ.
     * @param status Статус заказа.
     */
    public Order(User customer, Car car, String status) {
        this.customer = customer;
        this.car = car;
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public String getStatus() {
        return status;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer.getUsername() +
                ", car=" + car +
                ", status='" + status + '\'' +
                '}';
    }
}
