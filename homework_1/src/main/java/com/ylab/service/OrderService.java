package com.ylab.service;

import com.ylab.entity.Order;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

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

    /**
     * Вывод всех заказов или сообщения в случае отсутствия заказов
     */
    public void viewOrders() {
        if (orders.isEmpty()) {
            out.println("Список заказов пуст");
        } else {
            for (Order order : orders) {
                out.println(order);
            }
        }
    }
}
