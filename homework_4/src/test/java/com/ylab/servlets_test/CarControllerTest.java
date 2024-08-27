package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.CarController;
import com.ylab.entity.Car;
import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.CarFindDto;
import com.ylab.mapper.CarMapper;
import com.ylab.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testShowCars() throws Exception {
        List<Car> cars = List.of(new Car(), new Car());
        when(carService.getAllCars()).thenReturn(cars);
        when(carMapper.toCarDto(any(Car.class))).thenReturn(new CarDto());

        mockMvc.perform(get("/v1/carshop/show_cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(carService, times(1)).getAllCars();
        verify(carMapper, times(cars.size())).toCarDto(any(Car.class));
    }

    @Test
    public void testCreateCar() throws Exception {
        CarDto carDto = new CarDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Camry");

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");

        when(carMapper.toCar(carDto)).thenReturn(car);
        doNothing().when(carService).addCar(car);

        mockMvc.perform(post("/v1/carshop/staff/create_car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Автомобиль успешно создан!"));

        verify(carMapper, times(1)).toCar(carDto);
        verify(carService, times(1)).addCar(car);
    }

    @Test
    public void testEditCar() throws Exception {
        CarFindDto carDto = new CarFindDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Camry");

        Car car = new Car();
        car.setBrand("Toyota");
        car.setModel("Camry");

        when(carMapper.findCarDtoToCar(carDto)).thenReturn(car);
        doNothing().when(carService).editCar(anyString(), anyString(), any(Car.class));

        mockMvc.perform(patch("/v1/carshop/staff/edit_car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Автомобиль Toyota Camry успешно обновлен!"));

        verify(carMapper, times(1)).findCarDtoToCar(carDto);
        verify(carService, times(1)).editCar(anyString(), anyString(), any(Car.class));
    }

    @Test
    public void testRemoveCar() throws Exception {
        String brand = "Toyota";
        String model = "Camry";

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);

        when(carService.findByModelAndBrand(brand, model)).thenReturn(Optional.of(car));
        doNothing().when(carService).removeCar(car);

        mockMvc.perform(delete("/v1/carshop/staff/remove_car")
                        .param("brand", brand)
                        .param("model", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Автомобиль Toyota Camry успешно удален!"));

        verify(carService, times(1)).findByModelAndBrand(brand, model);
        verify(carService, times(1)).removeCar(car);
    }
}
