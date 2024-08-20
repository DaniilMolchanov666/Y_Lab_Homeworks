package com.ylab.controller;

import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.AccessService;
import com.ylab.service.UserService;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Контроллер для обработки запросов, связанных с пользователями
 */
public class UsersController {

    private final UserService userService;

    private final AccessService authorizationService;

    private final Scanner scanner = new Scanner(System.in);

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param userService сервис для работы с пользователями
     * @param authorizationService сервис авторизации
     */
    public UsersController(UserService userService, AccessService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    /**
     * Обработка запросов отображения всех пользователей из базы данных
     */
    public List<UserForShowDto> viewUsers() {
        List<UserForShowDto> users = new ArrayList<>();
        userService.getAllUsers().forEach(i -> users.add(userMapper.toUserForShowDto(i)));
        return users;
    }

    public String getUserInfo(String username) {
        return userMapper.toUserDto(userService.getUserByUsername(username)).toString();
    }

    public void editUserById(Integer id, UserUpdateDto userDto) {
        var foundedUser = userService.getUserById(id);
        userMapper.updateOwnProfile(userDto, foundedUser);
        userService.editUser(foundedUser);
    }

    public void editRoleByUser(UserForShowDto userDto) {
        var foundedUser = userService.getUserByUsername(userDto.getUsername());
        userMapper.updateRole(userDto, foundedUser);
        userService.editRoleUser(foundedUser);
    }

    public void removeUser(Integer id) throws Exception{
        userService.removeById(id);
    }
}
