package com.ylab.repository;

import com.ylab.entity.CarShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<CarShopUser, Integer> {

    /**
     * Поиск пользователя по имени
     * @param userName - имя пользователя
     * @return пользователя в виде Optional<CarShopUser>
     */
    Optional<CarShopUser> findByUsername(String userName);

    /**
     * Проверка наличия пользователя по имени
     * @param username - имя пользователя
     * @return true или false
     */
    boolean existsByUsername(String username);
}
