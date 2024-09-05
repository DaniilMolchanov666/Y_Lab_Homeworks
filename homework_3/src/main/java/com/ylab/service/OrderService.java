package com.ylab.service;

import com.ylab.entity.Order;
import com.ylab.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс управляет заказами на покупку автомобилей.
 */
public class OrderService {

    private final OrderRepository orderRepository = new OrderRepository();

    /**
     * Добавляет новый заказ на покупку автомобиля.
     *
     * @param order Заказ для добавления.
     */
    public void addOrder(Order order) {
        orderRepository.add(order);
    }


    /**
     * Возвращает список всех заказов на покупку автомобилей.
     *
     * @return Список всех заказов.
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderRepository.getAll());
    }

    /**
     * Обновляет информацию о заказе
     */
    public void editOrder(Order order) {
        orderRepository.edit(order);
    }

    /**
     * Находит заказа по данным автомобиля
     * @param brand - марка автомобиля, model - модель автомобиля
     * @return найденный заказ
     */
    public Order findOrderByCarName(String brand, String model) {
        return orderRepository.findOrderByCar(brand, model);
    }

    public Order findOrderByIdAndCarName(Integer id, String brand, String model) {
        return orderRepository.findCurrentUSerOrder(id, brand, model);
    }

    /**
     * Удаляет заказ на покупку автомобиля.
     *
     * @param order Заказ для удаления.
     */
    public void removeOrder(Order order) {
        orderRepository.remove(order);
    }

    /**
     * Вывод всех заказов или сообщения в случае отсутствия заказов
     * @return список всех заказов
     */
    public List<Order> viewOrders() {
        if (orderRepository.getAll().isEmpty()) {
            return Collections.emptyList();
        } return orderRepository.getAll();
    }
}
