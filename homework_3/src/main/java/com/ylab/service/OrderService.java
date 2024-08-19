package com.ylab.service;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

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
    public boolean addOrder(Order order) {
        return orderRepository.add(order);
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
     *
     * @return true - заказ был обновлен, false - произошла ошибка
     */
    public boolean editOrder(Order order) {
        return orderRepository.edit(order);
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
     */
    public void viewOrders() {
        if (orderRepository.getAll().isEmpty()) {
            out.println("Список заказов пуст");
        } else {
            for (Order order : orderRepository.getAll()) {
                out.println(order);
            }
        }
    }
}
