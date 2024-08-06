package com.ylab.repository;

import com.ylab.entity.CarShopEntity;

import java.util.List;

/**
 * Общий интерфейс для хранения сущностей автосалона
 * @param <T> сущность автосалона, реализующая интерфейс CarShopEntity
 */
public interface CarShopRepository <T extends CarShopEntity>{

    /**
     * Метод для добавления обьекта в коллекицю
     * @param t обьект для добавления
     */
    void add(T t);

    /**
     * Метод для получения всех обьектов из коллекции
     * @return коллекцию всех обьектов
     */
    List<T> getAll();

    /**
     * Метод для удаления обьекта из коллекции
     * @param t обьект для удаления
     */
    void remove(T t);
}
