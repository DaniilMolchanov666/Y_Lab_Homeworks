package com.ylab.entity;

/**
 * Перечисление представляет статусы для заказа
 * CREATED - задается сразу при создании заказа
 * IN_PROGRESS - задается персоналом при редактировании заказа
 * READY - задается персоналом при редактировании заказа
 */
public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    READY
}
