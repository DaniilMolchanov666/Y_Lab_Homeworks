package com.ylab.service;

import com.ylab.entity.Role;
import com.ylab.entity.User;

import static java.lang.System.out;

/**
 * Класс обеспечивает проверку прав доступа пользователей к определенным функциям
 * ADMIN - Имеет доступ ко всему
 * MANAGER -
 */
public class AccessService {
    /**
     * Проверяет, имеет ли пользователь необходимую роль.
     *
     * @param user Пользователь для проверки.
     * @param requiredRole Требуемая роль.
     * @return true, если пользователь имеет необходимую роль, иначе false.
     */
    public boolean hasSuitableRole(User user, Role requiredRole) {
        return user.getRole() == requiredRole;
    }

    /**
     * Проверяет, имеет ли пользователь роль администратора
     *
     * @param currentUser Пользователь для проверки.
     * @return true, если пользователь имеет необходимую роль, иначе false и сообщение об отмене доступа
     */
    public boolean isAdmin(User currentUser) {
        if (!hasSuitableRole(currentUser, Role.ADMIN)) {
            out.println("Доступ только для администрации!");
        }
        return hasSuitableRole(currentUser, Role.ADMIN);
    }

    /**
     * Проверяет, имеет ли пользователь роль администратора или менеджера
     *
     * @param currentUser Пользователь для проверки.
     * @return true, если пользователь имеет необходимую роль, иначе false и сообщение об отмене доступа
     */
    public boolean isManagerOrAdmin(User currentUser) {
        if (hasSuitableRole(currentUser, Role.CLIENT)) {
            out.println("Доступ только для персонала автосалона!");
        }
        return !hasSuitableRole(currentUser, Role.CLIENT);
    }

}
