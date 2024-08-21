package com.ylab.controller;

import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.mapper.UserMapper;
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

    private final Scanner scanner = new Scanner(System.in);

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param userService сервис для работы с пользователями
     */
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработка запросов отображения всех пользователей из базы данных
     * @return список пользователей без паролей
     */
    public List<UserForShowDto> viewUsers() {
        List<UserForShowDto> users = new ArrayList<>();
        userService.getAllUsers().forEach(i -> users.add(userMapper.toUserForShowDto(i)));
        return users;
    }

    /**
     * Обработка запросов отображения информации о пользователе
     * @param username - имя пользователя
     * @return информацию о пользователе
     */
    public String getUserInfo(String username) {
        return userMapper.toUserDto(userService.getUserByUsername(username)).toString();
    }

    /**
     * Обработка запросов редактирования пользователя по его id, сохраненного в сессии
     * @param id - id пользователя, userDto - пользователь
     */
    public void editUserById(Integer id, UserUpdateDto userDto) {
        var foundedUser = userService.getUserById(id);
        userMapper.updateOwnProfile(userDto, foundedUser);
        userService.editUser(foundedUser);
    }

    /**
     * Обработка запросов редактирования роли пользователя для администрации
     * @param userDto - данные пользователя для его поиска
     */
    public void editRoleByUser(UserForShowDto userDto) {
        var foundedUser = userService.getUserByUsername(userDto.getUsername());
        userMapper.updateRole(userDto, foundedUser);
        userService.editRoleUser(foundedUser);
    }

    /**
     * Обработка запросов удаления пользователя
     * @param id - id пользователя
     */
    public void removeUser(Integer id) throws Exception{
        userService.removeById(id);
    }
}
