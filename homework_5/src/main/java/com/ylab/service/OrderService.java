package com.ylab.service;

import com.ylab.entity.Order;
import com.ylab.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс управляет заказами на покупку автомобилей.
 */
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Добавляет нового заказа
     * @param order - заказ
     */
    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    /**
     * Поиск заказа по данным автомобиля
     * @param brand - брэнд
     * @param model - модель
     * @return  - искомый заказ
     */
    public Order findOrder(String model, String brand) {
        return orderRepository.findOrderByModelAndBrand(model, brand);
    }

    /**
     * Обновляет информацию о заказе
     * @param order - заказ для обновления
     * @param id - id заказа
     */
    public void editOrder(Integer id, Order order) {
        var foundedOrder = orderRepository.findById(id).orElseThrow(() ->
                new NullPointerException("Не существует такого заказа!"));
        foundedOrder.setStatus(order.getStatus());
        orderRepository.save(foundedOrder);
    }

    /**
     * Удаляет заказ на покупку автомобиля.
     * @param order Заказ для удаления.
     */
    public void removeOrder(Order order) {
        orderRepository.delete(order);
    }

    /**
     * Вывод всех заказов или сообщения в случае отсутствия заказов
     * @return список всех заказов
     */
    public List<Order> viewOrders() {
        return orderRepository.findAll();
    }
}
