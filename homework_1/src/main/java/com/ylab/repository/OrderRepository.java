package com.ylab.repository;

import com.ylab.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения заказов
 */
public class OrderRepository implements CarShopRepository<Order> {

    /**
     * Список для хранения заказов
     */
    private List<Order> orders = new ArrayList<>();

    @Override
    public void add(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public void remove(Order order) {
        orders.remove(order);
    }

}
