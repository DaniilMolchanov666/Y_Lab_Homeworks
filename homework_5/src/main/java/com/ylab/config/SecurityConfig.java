package com.ylab.config;

import com.ylab.service.CustomUserDetailsService;
import com.ylab.util.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Класс-конфигурация для распределения обязанностей автосалона по ролям (ADMIN, MANAGER, CLIENT),
 * а также настройки авторизации и регистрации пользователей
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Создание бина UserDetailsService для загрузки данных пользователя
     * @return новый объект типа UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    /**
     * Создание бина SecurityFilterChain для задания цепочки эндпоинтов и распределения ролей доступа
     * @return новый объект типа SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/carshop/login").permitAll()
                        .requestMatchers("/v1/carshop/register").permitAll()
                        .requestMatchers("/v1/carshop/admin/*").hasRole("ADMIN")
                        .requestMatchers("/v1/carshop/staff/*").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated())
                .formLogin(formLoginConfigurer ->
                        formLoginConfigurer
                                .loginProcessingUrl("/v1/carshop/login")
                                .defaultSuccessUrl("/v1/carshop/cars")
                                .permitAll()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    /**
     * Создание бина AuthenticationProvider для загрузки стандартного провайдера авторизации
     * @return новый объект типа AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Создание бина PasswordEncoder для шифрования пароля
     * @return новый объект типа PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создание бина AuthenticationManager для авторизации пользователя
     * @return новый объект типа AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
