package com.ylab.service;

import com.ylab.entity.Role;
import com.ylab.exception.NotAccessOperationException;

import java.util.Objects;

/**
 * Класс обеспечивает проверку прав доступа пользователей к определенным функциям
 * ADMIN - Имеет доступ ко всему
 * MANAGER -
 */
public class AccessService {

    public void isAdmin(String role) throws NotAccessOperationException {
        if (!Objects.equals(role, Role.ADMIN.name())) {
            throw new NotAccessOperationException();
        }
    }

    public void isManagerOrAdmin(String role) throws NotAccessOperationException {
        if (!(Objects.equals(role, Role.ADMIN.name()) || Objects.equals(role, Role.MANAGER.name()))) {
            throw new NotAccessOperationException();
        }
    }

    public void isClient(String role) throws NotAccessOperationException {
        if (!(Objects.equals(role, Role.CLIENT.name()))) {
            throw new NotAccessOperationException();
        }
    }
}
