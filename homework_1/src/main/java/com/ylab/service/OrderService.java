package com.ylab.service;

import com.ylab.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет заказами на покупку автомобилей.
 */
public class OrderService {
    private List<Order> orders = new ArrayList<>();

    /**
     * Добавляет новый заказ на покупку автомобиля.
     *
     * @param order Заказ для добавления.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Возвращает список всех заказов на покупку автомобилей.
     *
     * @return Список всех заказов.
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Удаляет заказ на покупку автомобиля.
     *
     * @param order Заказ для удаления.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
