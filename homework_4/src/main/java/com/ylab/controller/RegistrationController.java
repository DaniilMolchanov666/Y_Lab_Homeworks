package com.ylab.controller;

import com.ylab.entity.dto.UserDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов на регистрацию пользователя
 */
@RestController
@RequestMapping("/v1/carshop/")
@RequiredArgsConstructor
@Log4j2
public class RegistrationController {

    private final UserService userService;

    private final UserMapper userMapper;

    /**
     * Обработка запроса регистрации
     * @param userDto - данные пользователя для регистрации (имя, пароль и роль)
     * @return тело ответа
     */
    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован!"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким именем уже существует!")
    })
    @PostMapping(value = "register", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.addUser(userMapper.toUSer(userDto));
        log.log(Level.INFO, "Пользователь зарегистрирован!");
        return ResponseEntity.ok("Пользователь %s сохранен!".formatted(userDto));
    }
}
