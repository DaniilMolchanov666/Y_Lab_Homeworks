package com.ylab.repository;

import com.ylab.entity.CarShopEntity;
import com.ylab.out.LiquibaseConfig;

import java.sql.Connection;
import java.util.List;

/**
 * Абстрактный класс для взаимодействия с таблицами баз данных
 * @param <T> сущность автосалона, реализующая интерфейс CarShopEntity
 */
public abstract class CarShopRepository <T extends CarShopEntity>{

    /**
     * Подключение к БД, общее для всех репозиториев, которое задается по умолчанию
     */
    Connection connection = LiquibaseConfig.dbConnection;
    /**
     * Метод для добавления обьекта
     * @param t обьект для добавления
     */
    abstract void add(T t);

    /**
     * Метод для получения всех обьектов
     *
     * @return коллекцию всех обьектов
     */
    abstract List<T> getAll();

    /**
     * Метод для удаления обьекта
     * @param t обьект для удаления
     */
    abstract void remove(T t);

    /**
     * Метод для обновления обьекта
     * @param t обьект для обновления
     */
    abstract boolean edit(T t);

    /**
     * Метод для обновления подключения к базе данных
     * @param con обьект типа Connection для задания подключения к другой базе данных (используется в тестах)
     */
    public void setNewConnection(Connection con) {
        connection = con;
    }
}
