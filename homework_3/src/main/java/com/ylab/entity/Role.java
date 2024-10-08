package com.ylab.entity;

/**
 * Перечисление представляет роли пользователей.
 * ADMIN - Имеет доступ ко всему
 * MANAGER - имеет доступ ко всему, кроме просмотра и редактирования пользователей
 * CLIENT - имеет доступ к просмотру и созданию заказов, просмотру автомобилей и поиску
 */
public enum Role {
    ADMIN,
    MANAGER,
    CLIENT
}
