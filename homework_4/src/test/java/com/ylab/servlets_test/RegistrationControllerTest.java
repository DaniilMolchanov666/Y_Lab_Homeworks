package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.RegistrationController;
import com.ylab.entity.Role;
import com.ylab.entity.dto.UserDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.ylab.entity.User;
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
    public void testRegisterUser() throws Exception {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("testPassword");
        userDto.setRole(Role.valueOf("ADMIN"));

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole("USER");

        when(userMapper.toUSer(userDto)).thenReturn(user);
        doNothing().when(userService).addUser(user);

        // Act & Assert
        mockMvc.perform(post("/v1/carshop/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь " + userDto + " сохранен!"));

        verify(userMapper, times(1)).toUSer(userDto);
        verify(userService, times(1)).addUser(user);
    }
}