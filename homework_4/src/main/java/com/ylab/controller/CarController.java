package com.ylab.controller;

import com.ylab.entity.dto.CarDto;
import com.ylab.entity.dto.CarFindDto;
import com.ylab.mapper.CarMapper;
import com.ylab.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с автомобилями
 */
@RestController
@RequiredArgsConstructor
@Log4j2
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @GetMapping(value = "/carshop/show_cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CarDto>> showCars() {
        List<CarDto> listOfCars = carService.getAllCars().stream().map(carMapper::toCarDto).toList();
        return ResponseEntity.ok(listOfCars);
    }

    @PostMapping(value = "/admin/create_car", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarDto carDto) {
        carService.addCar(carMapper.toCar(carDto));
        return ResponseEntity.ok(carDto);
    }

    @PatchMapping(value = "/admin/edit_car", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarFindDto> editCar(@Valid @RequestBody CarFindDto carDto) {
        carService.editCar(carDto.getBrand(), carDto.getModel(), carMapper.findCarDtoToCar(carDto));
        return ResponseEntity.ok(carDto);
    }

    @DeleteMapping(value = "/admin/remove_car", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCar(@Valid @RequestParam(required = false) String brand,
                                            @Valid @RequestParam(required = false) String model) {
        carService.removeCar(carService.findByModelAndBrand(brand, model).orElse(null));
        return ResponseEntity.ok("Автомобиль удален!");

    }

}
