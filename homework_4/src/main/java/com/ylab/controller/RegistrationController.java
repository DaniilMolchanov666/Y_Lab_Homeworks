package com.ylab.controller;

import com.ylab.entity.dto.UserDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping(value = "/carshop/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.addUser(userMapper.toUSer(userDto));
        return ResponseEntity.ok("Пользователь сохранен!");
    }
}
