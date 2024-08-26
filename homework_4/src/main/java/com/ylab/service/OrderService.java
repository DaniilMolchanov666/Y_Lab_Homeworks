package com.ylab.service;

import com.ylab.entity.Order;
import com.ylab.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет заказами на покупку автомобилей.
 */
@Repository
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrder(String model, String brand) {
        return orderRepository.findOrderByModelAndBrand(model, brand);
    }

    /**
     * Возвращает список всех заказов на покупку автомобилей.
     *
     * @return Список всех заказов.
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderRepository.findAll());
    }

    /**
     * Обновляет информацию о заказе
     *
     * @return true - заказ был обновлен, false - произошла ошибка
     */
    public void editOrder(String brand, String model, Order order) {
        Order foundedOrder = orderRepository.findOrderByModelAndBrand(model, brand);
        orderRepository.delete(foundedOrder);
        orderRepository.save(order);
    }

    /**
     * Удаляет заказ на покупку автомобиля.
     *
     * @param order Заказ для удаления.
     */
    public void removeOrder(Order order) {
        orderRepository.delete(order);
    }

    /**
     * Вывод всех заказов или сообщения в случае отсутствия заказов
     */
    public List<Order> viewOrders() {
        return orderRepository.findAll();
    }
}
