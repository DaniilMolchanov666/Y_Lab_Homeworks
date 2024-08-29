package com.ylab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.entity.User;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import com.ylab.entity.dto.user.UserUpdateDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
@RestController
@RequestMapping("/v1/carshop/")
@AllArgsConstructor
@Log4j2
public class UsersController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final ObjectMapper objectMapper;

    /**
     * Обработка запросов отображения всех пользователей из базы данных (только для администрации)
     * @return список всех пользователей и статус 200 OK
     */
    @Operation(summary = "Вывод всех пользователей (для администрации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список зарегистрированных пользователей")
    })
    @GetMapping(value = "admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> viewUsers() throws JsonProcessingException {
        List<String> users = userService.getAllUsers().stream()
                .map(user -> {
                    try {
                        return objectMapper.writeValueAsString(userMapper.toUserForShowDto(user));
                    } catch (JsonProcessingException e) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Некорректный формат!").getBody();
                    }
                }).toList();
        log.log(Level.INFO, users);
        return ResponseEntity.ok("Список зарегистрированных пользователей:\n" + users);
    }

    /**
     * Обработка запросов редактирования пользователя по его id, сохраненного в сессии
     * @param userUpdateDto - данные пользователя для обновления (имя и пароль)
     * @return тело ответа
     */
    @Operation(summary = "Обновление данных текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлен!"),
            @ApiResponse(responseCode = "404", description = "Нет пользователя с такими именем и паролем!"),

    })
    @PatchMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editUser(@RequestBody UserUpdateDto userUpdateDto) {
        var userDetails = userService.getCurrentAuthenticatedUser();
        int id = userService.getUserByUsername(userDetails.getUsername()).getId();

        var user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());

        userMapper.updateOwnProfile(userUpdateDto, user);
        userService.editUser(id, user);
        log.log(Level.INFO, "Пользователь обновлен!");
        return ResponseEntity.ok("Пользователь обновлен!");
    }

    /**
     * Обработка запросов редактирования роли пользователя по его имени (для администрации)
     * @param userUpdateDto - данные о пользователя для обновления (имя и роль)
     * @return тело ответа
     */
    @Operation(summary = "Обновление роли пользователя (для администрации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Нет пользователя с таким именем!"),

    })
    @PatchMapping(value = "admin/user-role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editUserRole(@RequestBody UserForShowAndUpdateRoleDto userUpdateDto) {
        User foundedUser = userService.getUserByUsername(userUpdateDto.getUsername());
        int id = foundedUser.getId();

        userMapper.updateRole(userUpdateDto, foundedUser);
        userService.editUser(id, foundedUser);

        return ResponseEntity.ok("Роль пользователя '%s' изменена на '%s'!"
                .formatted(userUpdateDto.getUsername(), userUpdateDto.getRole()));
    }

    /**
     * Обработка запросов удаления пользователя
     * @param userName - данные о пользователя для удаления (имя)
     * @return тело ответа
     */
    @Operation(summary = "Удаление пользователя (для администрации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Нет пользователя с таким именем!"),

    })
    @DeleteMapping(value = "admin/users/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeUser(@PathVariable String userName) throws NullPointerException {
        userService.removeUser(userService.getUserByUsername(userName));
        return ResponseEntity.ok("Пользователь '%s' успешно удален!".formatted(userName));
    }
}
