package com.ylab.service;

import com.ylab.entity.Order;
import com.ylab.repository.OrderRepository;
import jakarta.transaction.Transactional;
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

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findOrder(String model, String brand) {
        return orderRepository.findOrderByModelAndBrand(model, brand);
    }

    /**
     * Обновляет информацию о заказе
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
    @Transactional
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
