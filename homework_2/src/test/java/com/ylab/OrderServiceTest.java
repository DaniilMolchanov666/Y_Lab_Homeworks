package com.ylab;

import com.ylab.entity.Car;
import com.ylab.entity.Order;
import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderService orderService;
//
//    @Test
//    public void testCreateOrder() {
//
//        var user2 = new User("Mark", "5678", Role.MANAGER);
//        var car = Car.builder()
//                .model("MAZDA 3")
//                .brand("MAZDA")
//                .condition("SALE")
//                .price("18000000")
//                .year("2018")
//                .build();
//        var order = new Order(user2, car, "sale");
//        orderService.addOrder(order);
//
//        verify(orderService, times(1)).addOrder(order);
//    }
//
//
//    @Test
//    public void testGetAllOrders() {
//
//        List<Order> orderList = List.of(new Order());
//
//        when(orderService.getAllOrders()).thenReturn(orderList);
//
//        List<Order> orders = orderService.getAllOrders();
//
//        assertThat(orders).hasSize(1);
//    }
//
//    @Test
//    public void testRemoveOrder() {
//
//        var user2 = new User("Mark", "5678", Role.MANAGER);
//        var car = Car.builder()
//                .model("MAZDA 3")
//                .brand("MAZDA")
//                .condition("SALE")
//                .price("18000000")
//                .year("2018")
//                .build();
//        var order = new Order(user2, car, "sale");
//
//        orderService.addOrder(order);
//        orderService.removeOrder(order);
//
//        verify(orderService, times(1)).removeOrder(order);
//    }

}
