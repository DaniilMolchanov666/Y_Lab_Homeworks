package com.ylab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.entity.dto.car.CarForCreateDto;
import com.ylab.entity.dto.car.CarFindDto;
import com.ylab.mapper.CarMapper;
import com.ylab.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с автомобилями
 */
@RestController
@RequestMapping("/v1/carshop/")
@RequiredArgsConstructor
@Log4j2
public class CarController {

    private final CarService carService;

    private final CarMapper carMapper;

    private final ObjectMapper objectMapper;

    /**
     * Обработка запроса создания заказа
     * @return список автомобилей и статус 200 ОК
     */
    @Operation(summary = "Вывод автомобилей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список автомобилей"),
            @ApiResponse(responseCode = "409", description = "Некорректный формат!")
    })
    @SneakyThrows
    @GetMapping(value = "cars", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> showCars() {
        List<String> listOfCars = carService.getAllCars().stream()
                .map(car -> {
                    try {
                        return objectMapper.writeValueAsString(carMapper.toCarDto(car));
                    } catch (JsonProcessingException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Некорректный формат!").getBody();
                    }
                }).toList();
        log.log(Level.INFO, listOfCars);
        return ResponseEntity.ok("""
                Добро пожаловать в наш автосалон!
                Список доступных для заказа автомобилей:
                %s
                """.
                formatted(listOfCars));
    }

    /**
     * Обработка запроса создания заказа (для персонала)
     * @return сообщение об успешном создании автомобиля и статус 201 CREATED
     */
    @Operation(summary = "Создание автомобиля (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно создан!"),
            @ApiResponse(responseCode = "409", description = "Автомобиль с такими данными уже существует!")
    })
    @PostMapping(value = "staff/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCar(@Valid @RequestBody CarForCreateDto carDto) {
        carService.addCar(carMapper.toCar(carDto));
        log.log(Level.INFO, "Автомобиль '%s' успешно создан!".formatted(carDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("Автомобиль успешно создан!");
    }

    /**
     * Обработка запроса редактирования автомобиля (для персонала)
     * @return сообщение об успешном обновлении автомобиля и статус 200 OK
     */
    @Operation(summary = "Редактирование автомобиля (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно обновлен!"),
            @ApiResponse(responseCode = "404", description = "Не существует автомобиля такого брэнда и модели!")
    })
    @PatchMapping(value = "staff/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editCar(@Valid @RequestBody CarFindDto carDto) {
        carService.editCar(carDto.getBrand(), carDto.getModel(), carMapper.findCarDtoToCar(carDto));
        log.log(Level.INFO, "Автомобиль %s %s успешно обновлен!".formatted(carDto.getBrand(), carDto.getModel()));
        return ResponseEntity.ok("Автомобиль %s %s успешно обновлен!".formatted(carDto.getBrand(), carDto.getModel()));
    }

    /**
     * Обработка запроса удаления автомобиля (для персонала)
     * @return сообщение об успешном удалении автомобиля и статус 200 OK
     */
    @Operation(summary = "Удаление автомобиля (для персонала)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автомобиль успешно удален!"),
            @ApiResponse(responseCode = "404", description = "Не существует автомобиля такого брэнда и модели!")
    })
    @DeleteMapping(value = "staff/cars", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeCar(@Valid @RequestParam(value = "brand", required = false) String brand,
                                            @Valid @RequestParam(value = "model", required = false) String model) {
        carService.removeCar(carService.findByModelAndBrand(brand, model).orElse(null));
        log.log(Level.INFO, "Автомобиль %s %s успешно удален!".formatted(brand, model));
        return ResponseEntity.ok("Автомобиль %s %s успешно удален!".formatted(brand, model));
    }
}
