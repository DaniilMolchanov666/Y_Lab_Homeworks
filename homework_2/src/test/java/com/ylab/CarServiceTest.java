package com.ylab;

import com.ylab.entity.Car;
import com.ylab.exception.ValidationCarDataException;
import com.ylab.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarService carService;

    @Test
    public void testCarValuesValidation() throws ValidationCarDataException {

        var car = Car.builder().model("AUDI Q8")
                .brand("AUDI")
                .year("2900")
                .price("abc")
                .condition("sale")
                .build();
    }

//    @Test
//    public void testCreateCar() {
//
//        var car = Car.builder()
//                .model("MAZDA 3")
//                .brand("MAZDA")
//                .condition("SALE")
//                .price("18000000")
//                .year("2018")
//                .build();
//
//        carService.addCar(car);
//
//        verify(carService, times(1)).addCar(car);
//    }


//    @Test
//    public void testGetAllOrders() {
//        var car = Car.builder().model("AUDI Q8")
//                .brand("AUDI")
//                .year("2900")
//                .price("abc")
//                .condition("sale")
//                .build();
//
//        List<Car> carList = List.of(car);
//
//        when(carService.getAllCars()).thenReturn(carList);
//
//        List<Car> cars = carService.getAllCars();
//
//        assertThat(cars).hasSize(1);
//    }

//    @Test
//    public void testRemoveOrder() {
//
//        var car = Car.builder()
//                .model("MAZDA 3")
//                .brand("MAZDA")
//                .condition("SALE")
//                .price("18000000")
//                .year("2018")
//                .build();
//
//        carService.addCar(car);
//        carService.removeCar(car);
//
//        verify(carService, times(1)).removeCar(car);
//    }
}
