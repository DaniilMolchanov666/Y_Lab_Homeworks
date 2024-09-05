package com.ylab.controller;

import com.ylab.entity.dto.user.UserForRegisterDto;
import com.ylab.service.CustomUserDetailsService;
import com.ylab.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для обработки запросов, связанных с авторизацией
 */
@RestController
@RequestMapping("/v1/carshop")
@Log4j2
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService carShopUserDetails;

    private final JwtUtil jwtUtil;

    /**
     * Обработка запроса авторизации пользователя
     * @param userForRegisterDto - сущность, предоставляющая имя и пароль пользователя
     * @return jwt токен и статус 200 ОК
     */
    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "409")
    })
    @PostMapping(value = "/login")
    public ResponseEntity<String> authUser(@RequestBody UserForRegisterDto userForRegisterDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userForRegisterDto.getUsername(),
                        userForRegisterDto.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = jwtUtil.generateToken(carShopUserDetails.loadUserByUsername(userForRegisterDto.getUsername()));

        return ResponseEntity.ok().header("Authorization", "Bearer " + jwt).body("Авторизация успешно пройдена!");
    }
}
