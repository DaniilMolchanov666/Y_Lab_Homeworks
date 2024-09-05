package com.ylab.service;

import com.ylab.entity.CarShopUser;
import com.ylab.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс управляет пользователями в системе.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Добавляет нового пользователя в систему.
     * @param user Пользователь для добавления.
     */
    public void addUser(CarShopUser user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void editUser(Integer id, CarShopUser user) {
        var foundedUser = userRepository.findById(id).orElseThrow(NullPointerException::new);
        foundedUser.setUsername(user.getUsername());
        foundedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        foundedUser.setRole(user.getRole());
        userRepository.save(user);
    }

    public UserDetails getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        throw new NullPointerException("Нет пользователя с такими именем и паролем!");
    }

    /**
     * Возвращает пользователя по имени пользователя.
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем или null, если пользователь не найден.
     */
    public CarShopUser getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NullPointerException("Нет пользователя с таким именем!"));
    }

    /**
     * Возвращает список всех пользователей в системе.
     * @return Список всех пользователей.
     */
    public List<CarShopUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void removeUser(CarShopUser user) {
        userRepository.delete(user);
    }
}