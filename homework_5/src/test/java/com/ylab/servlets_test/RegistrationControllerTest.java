package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.RegistrationController;
import com.ylab.entity.CarShopUser;
import com.ylab.entity.Role;
import com.ylab.entity.dto.user.UserForRegisterDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Тест на проверку регистрации пользователя и получения статуса 200 ОК")
    public void testRegisterUser() throws Exception {
        // Arrange
        UserForRegisterDto userDto = new UserForRegisterDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setRole(Role.valueOf("ADMIN"));

        CarShopUser user = new CarShopUser();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole("USER");

        when(userMapper.toUser(userDto)).thenReturn(user);
        doNothing().when(userService).addUser(user);

        // Act & Assert
        mockMvc.perform(post("/v1/carshop/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь " + userDto + " сохранен!"));

        verify(userMapper, times(1)).toUser(userDto);
        verify(userService, times(1)).addUser(user);
    }
}