package com.ylab.service;

import com.ylab.entity.CarShopUserDetails;
import com.ylab.entity.CarShopUser;
import com.ylab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Класс-сервис для загрузки нового пользователя при авторизации
 */
@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Авторизация пользователя по имени
     * @param username - имя пользователя
     * @return объект UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CarShopUser> foundedUser = userRepository.findByUsername(username);
        return new CarShopUserDetails(foundedUser.orElseThrow(()
                -> new UsernameNotFoundException("Пользователь не найден!")));
    }
}
