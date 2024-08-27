package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.UsersController;
import com.ylab.entity.dto.UserForShowDto;
import com.ylab.entity.dto.UserUpdateDto;
import com.ylab.mapper.UserMapper;
import com.ylab.entity.User;
import com.ylab.service.CustomUserDetailsService;
import com.ylab.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testViewUsers() throws Exception {
        List<User> users = List.of(new User(), new User());
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.toForShowDto(any(User.class))).thenReturn(new UserForShowDto());

        mockMvc.perform(get("/v1/carshop/admin/show_users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).getAllUsers();
        verify(userMapper, times(users.size())).toForShowDto(any(User.class));
    }

    @Test
    public void testEditUser() throws Exception {
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();
        User user = new User();

        when(userService.getCurrentAuthenticatedUser()).thenReturn((UserDetails) customUserDetailsService);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doNothing().when(userMapper).updateOwnProfile(any(UserUpdateDto.class), any(User.class));
        doNothing().when(userService).editUser(anyInt(), any(User.class));

        mockMvc.perform(patch("/v1/carshop/edit_profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь обновлен!"));

        verify(userService, times(1)).getCurrentAuthenticatedUser();
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(userMapper, times(1)).updateOwnProfile(any(UserUpdateDto.class), any(User.class));
        verify(userService, times(1)).editUser(anyInt(), any(User.class));
    }

    @Test
    public void testEditUserRole() throws Exception {
        UserForShowDto userUpdateDto = new UserForShowDto();
        User user = new User();

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        doNothing().when(userMapper).updateRole(any(UserForShowDto.class), any(User.class));
        doNothing().when(userService).editUser(anyInt(), any(User.class));

        mockMvc.perform(patch("/v1/carshop/admin/edit_role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Роль пользователя '%s' изменена на '%s'!", userUpdateDto.getUsername(), userUpdateDto.getRole())));

        verify(userService, times(1)).getUserByUsername(anyString());
        verify(userMapper, times(1)).updateRole(any(UserForShowDto.class), any(User.class));
        verify(userService, times(1)).editUser(anyInt(), any(User.class));
    }

    @Test
    public void testRemoveUser() throws Exception {
        String userName = "testUser";
        User user = new User();

        when(userService.getUserByUsername(userName)).thenReturn(user);
        doNothing().when(userService).removeUser(user);

        mockMvc.perform(delete("/v1/carshop/admin/remove_profile/{userName}", userName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Пользователь '%s' успешно удален!", userName)));

        verify(userService, times(1)).getUserByUsername(userName);
        verify(userService, times(1)).removeUser(user);
    }
}
