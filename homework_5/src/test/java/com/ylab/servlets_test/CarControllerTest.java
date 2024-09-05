package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.CarController;
import com.ylab.entity.Car;
import com.ylab.entity.dto.car.request.CarRequestDto;
import com.ylab.entity.dto.car.response.CarFindDto;
import com.ylab.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    private Car carRequestDto;
    private Car carFindDto;

    @BeforeEach
    public void setUp() {
        carRequestDto = new Car();
        carRequestDto.setBrand("Toyota");
        carRequestDto.setModel("Camry");

        carFindDto = new Car();
        carFindDto.setBrand("Toyota");
        carFindDto.setModel("Camry");
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Тест на проверку вывода автомобилей и получения статуса 200 ОК")
    public void testShowCars() throws Exception {
        when(carService.getAllCars(any())).thenReturn(Collections.singletonList(carRequestDto));

        mockMvc.perform(get("/v1/carshop/cars")
                        .param("page", "1")
                        .param("limit", "10")
                        .param("sortValue", "brand"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Camry"));
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("Тест на проверку нахождения определенного автомобиля и получения статуса 200 ОК")
    public void testShowCurrentCar() throws Exception {
        when(carService.findCar("Camry")).thenReturn(carRequestDto);

        mockMvc.perform(get("/v1/carshop/cars/Camry"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    @DisplayName("Тест на создание автомобиля и получения статуса 201 CREATED")
    public void testCreateCar() throws Exception {
        mockMvc.perform(post("/v1/carshop/staff/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Автомобиль успешно создан!"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    @DisplayName("Тест на проверку обновления автомобиля и получения статуса 200 ОК")
    public void testEditCar() throws Exception {
        mockMvc.perform(patch("/v1/carshop/staff/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carFindDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Автомобиль Toyota Camry успешно обновлен!"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "MANAGER"})
    @DisplayName("Тест на проверку удаления автомобиля и получения статуса 200 ОК")
    public void testRemoveCar() throws Exception {
        when(carService.findByModelAndBrand("Toyota", "Camry")).thenReturn(Optional.of(carRequestDto));

        mockMvc.perform(delete("/v1/carshop/staff/cars")
                        .param("brand", "Toyota")
                        .param("model", "Camry"))
                .andExpect(status().isOk())
                .andExpect(content().string("Автомобиль Toyota Camry успешно удален!"));
    }
}
