package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.entity.CarShopUser;
import com.ylab.entity.CarShopUserDetails;
import com.ylab.entity.dto.user.UserForRegisterDto;
import com.ylab.service.CustomUserDetailsService;
import com.ylab.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailsService carShopUserDetails;

    @MockBean
    private JwtUtil jwtUtil;

    private UserForRegisterDto userForRegisterDto;

    private CarShopUserDetails userDetails;

    private String jwtToken;

    @BeforeEach
    public void setUp() {
        userForRegisterDto = new UserForRegisterDto();
        userForRegisterDto.setUsername("user");
        userForRegisterDto.setPassword("password");

        CarShopUser carShopUser = new CarShopUser();
        carShopUser.setUsername("user");
        carShopUser.setPassword("password");
        carShopUser.setRole("ADMIN");

        userDetails = new CarShopUserDetails(carShopUser);
        jwtToken = "test.jwt.token";
    }

    @Test
    public void testAuthUser() throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(carShopUserDetails.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn(jwtToken);

        mockMvc.perform(post("/v1/carshop/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForRegisterDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", "Bearer " + jwtToken))
                .andExpect(content().string("Авторизация успешно пройдена!"));
    }

    @Test
    public void testAuthUserInvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/v1/carshop/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userForRegisterDto)))
                .andExpect(status().isUnauthorized());
    }
}
