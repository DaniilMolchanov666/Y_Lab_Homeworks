package com.ylab.repository;

import com.ylab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для хранения пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUserName();
}
