package com.ylab.controller;

import com.ylab.entity.dto.CarDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с автомобилями
 */
@RestController("/carshop")
@RequiredArgsConstructor
@Log4j2
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    @GetMapping(value = "/show_cars", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<CarDto> editCar(@RequestParam(value = "brand") String brand,
                                          @RequestParam(value = "model") String model,
                                          @Valid @RequestBody CarDto carDto, @PathVariable Integer id) {
        carService.editCar(brand, model, carMapper.toCar(carDto));
        return ResponseEntity.ok(carDto);
    }

    @DeleteMapping(value = "/admin/remove_car", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarDto> removeCar(@Valid @RequestBody CarDto carDto) {
        carService.removeCar(carMapper.toCar(carDto));
        return ResponseEntity.ok(carDto);
    }

}
