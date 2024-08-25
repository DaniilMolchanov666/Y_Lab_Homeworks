package com.ylab.controller;

import com.ylab.entity.dto.UserDto;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
@RestController("/carshop")
@AllArgsConstructor
public class UsersController {

    private final UserService userService;

    private final UserMapper userMapper;

    /**
     * Обработка запросов отображения всех пользователей из базы данных
     * @return список пользователей без паролей
     */
    @GetMapping(value = "/admin/show_users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserForShowDto>> viewUsers() {
        List<UserForShowDto> users = new ArrayList<>();
        userService.getAllUsers().forEach(i -> users.add(userMapper.toUserForShowDto(i)));
        return ResponseEntity.ok(users);
    }

    /**
     * Обработка запросов редактирования пользователя по его id, сохраненного в сессии
     * @param id - id пользователя, userDto - пользователь
     */
    @PatchMapping(value = "/edit_profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public void editUserById(Integer id, UserUpdateDto userDto) {
        var foundedUser = userService.getUserById(id);
        userMapper.updateOwnProfile(userDto, foundedUser);
        userService.editUser(foundedUser);
    }

    /**
     * Обработка запросов удаления пользователя
     */
    @DeleteMapping(value = "/remove_profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeUser(@RequestBody UserDto userDto) throws Exception{
        userService.removeUser(userMapper.toUSer(userDto));
    }
}
