package com.ylab.service;

import com.ylab.entity.Role;
import com.ylab.entity.User;

/**
 * Класс обеспечивает авторизацию пользователей.
 */
public class AuthorizationService {
    /**
     * Проверяет, имеет ли пользователь необходимую роль.
     *
     * @param user Пользователь для проверки.
     * @param requiredRole Требуемая роль.
     * @return true, если пользователь имеет необходимую роль, иначе false.
     */
    public boolean isAuthorized(User user, Role requiredRole) {
        return user.getRole() == requiredRole;
    }
}
