package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.OrderController;
import com.ylab.entity.Car;
import com.ylab.entity.CarShopUser;
import com.ylab.entity.Order;
import com.ylab.entity.OrderStatus;
import com.ylab.entity.dto.car.response.CarFindDto;
import com.ylab.entity.dto.order.request.OrderRequestDto;
import com.ylab.entity.dto.order.reponse.OrderFindDto;
import com.ylab.mapper.OrderMapper;
import com.ylab.service.CarService;
import com.ylab.service.OrderService;
import com.ylab.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private CarService carService;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Тест на проверку вывода заказов и получения статуса 200 ОК")
    public void testShowOrders() throws Exception {
        List<Order> orders = List.of(new Order(), new Order());
        when(orderService.viewOrders()).thenReturn(orders);
        when(orderMapper.toOrderDto(any(Order.class))).thenReturn(new OrderRequestDto());

        mockMvc.perform(get("/v1/carshop/show_orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(orderService, times(1)).viewOrders();
        verify(orderMapper, times(orders.size())).toOrderDto(any(Order.class));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Тест на проверку создания заказа и получения статуса 201 CREATED")
    public void testCreateOrder() throws Exception {
        CarFindDto carDto = new CarFindDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Camry");

        CarShopUser user = new CarShopUser();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPassword");

        Order order = new Order();
        order.setCustomer(user);
        order.setStatus(OrderStatus.CREATED.name());

        when(userService.getCurrentAuthenticatedUser()).thenReturn((UserDetails) user);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(carService.findByModelAndBrand(anyString(), anyString())).thenReturn(Optional.of(new Car()));

        verify(orderService, times(1)).addOrder(order);
        when(orderMapper.toOrderDto(any(Order.class))).thenReturn(new OrderRequestDto());

        mockMvc.perform(post("/v1/carshop/create_order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).getCurrentAuthenticatedUser();
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(carService, times(1)).findByModelAndBrand(anyString(), anyString());
        verify(orderService, times(1)).addOrder(any(Order.class));
        verify(orderMapper, times(1)).toOrderDto(any(Order.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    @DisplayName("Тест на проверку обновления статуса заказа и получения статуса 200 ОК")
    public void testChangeOrderStatus() throws Exception {
        OrderFindDto orderFindDto = new OrderFindDto();
        orderFindDto.setBrand("Toyota");
        orderFindDto.setModel("Camry");
        orderFindDto.setStatus(OrderStatus.IN_PROGRESS.name());

        Order order = new Order();
        order.setStatus(OrderStatus.CREATED.name());

        when(orderService.findOrder(anyString(), anyString())).thenReturn(order);
        doNothing().when(orderService).editOrder(anyInt(), any(Order.class));

        mockMvc.perform(patch("/v1/carshop/staff/edit_order_status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderFindDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Статус заказа изменен на '%s'".formatted(orderFindDto.getStatus())));

        verify(orderService, times(1)).findOrder(anyString(), anyString());
        verify(orderService, times(1)).editOrder(anyInt(), any(Order.class));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    @DisplayName("Тест на проверку удаления заказа и получения статуса 200 ОК")
    public void testRemoveOrder() throws Exception {
        OrderFindDto orderFindDto = new OrderFindDto();
        orderFindDto.setBrand("Toyota");
        orderFindDto.setModel("Camry");

        Order order = new Order();

        when(orderService.findOrder(anyString(), anyString())).thenReturn(order);
        doNothing().when(orderService).removeOrder(any(Order.class));

        mockMvc.perform(delete("/v1/carshop/staff/remove_order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderFindDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Заказ %s удален!".formatted(orderFindDto)));

        verify(orderService, times(1)).findOrder(anyString(), anyString());
        verify(orderService, times(1)).removeOrder(any(Order.class));
    }
}