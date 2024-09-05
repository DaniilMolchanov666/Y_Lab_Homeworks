package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.UsersController;
import com.ylab.entity.CarShopUser;
import com.ylab.entity.CarShopUserDetails;
import com.ylab.entity.Role;
import com.ylab.entity.dto.user.UserForShowAndUpdateRoleDto;
import com.ylab.entity.dto.user.UserUpdateDto;
import com.ylab.mapper.UserMapper;
import com.ylab.service.CustomUserDetailsService;
import com.ylab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private UserForShowAndUpdateRoleDto userForShowAndUpdateRoleDto;
    private UserUpdateDto userUpdateDto;
    private CarShopUser carShopUser;

    @BeforeEach
    public void setUp() {
        userForShowAndUpdateRoleDto = new UserForShowAndUpdateRoleDto();
        userForShowAndUpdateRoleDto.setUsername("user");
        userForShowAndUpdateRoleDto.setRole(Role.valueOf("ROLE_USER"));

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setUsername("user");
        userUpdateDto.setPassword("password");

        carShopUser = new CarShopUser();
        carShopUser.setId(1);
        carShopUser.setUsername("user");
        carShopUser.setPassword("password");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Тест на проверку вывода пользователей и получения статуса 200 ОК")
    public void testViewUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(carShopUser));

        mockMvc.perform(get("/v1/carshop/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value("user"))
                .andExpect(jsonPath("$[0].role").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(username = "user", roles = "CLIENT")
    @DisplayName("Тест на проверку редактирования профиля текущего пользователя и получения статуса 200 ОК")
    public void testEditUser() throws Exception {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(new CarShopUserDetails(carShopUser));
        when(userService.getUserByUsername("user")).thenReturn(carShopUser);

        mockMvc.perform(patch("/v1/carshop/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь обновлен!"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Тест на проверку изменения роли пользователя и получения статуса 200 ОК")
    public void testEditUserRole() throws Exception {
        when(userService.getUserByUsername("user")).thenReturn(carShopUser);

        mockMvc.perform(patch("/v1/carshop/admin/user-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForShowAndUpdateRoleDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Роль пользователя 'user' изменена на 'ROLE_USER'!"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Тест на проверку удаления пользователя и получения статуса 200 ОК")
    public void testRemoveUser() throws Exception {
        when(userService.getUserByUsername("user")).thenReturn(carShopUser);

        mockMvc.perform(delete("/v1/carshop/admin/users/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь 'user' успешно удален!"));
    }
}
