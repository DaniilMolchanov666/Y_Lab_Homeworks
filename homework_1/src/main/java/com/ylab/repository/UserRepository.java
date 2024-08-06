package com.ylab.repository;

import com.ylab.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для хранения пользователей
 */
public class UserRepository implements CarShopRepository<User> {

    /**
     * Список для хранения пользователей
     */
    private List<User> users = new ArrayList<>();

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }
}
