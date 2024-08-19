package com.ylab.utils;

import com.ylab.entity.CarShopEntity;
import com.ylab.out.LiquibaseConfig;
import org.postgresql.util.PSQLException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//TODO доделать и внедрить мезанизм для транзакций любой таблицы
public class JDBCManager {

    private static final Connection connection = LiquibaseConfig.dbConnection;

    public static boolean insert(CarShopEntity carShopEntity) {

        String nameOfTable = carShopEntity.getClass().getSimpleName().toLowerCase() + "s";
        Field[] fields = carShopEntity.getClass().getDeclaredFields();

        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();

        for (int i  = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            stringBuilder1.append(" ").append(fields[i].getName()).append(",");
            stringBuilder2.append("?,");
        }
        stringBuilder2.deleteCharAt(stringBuilder2.toString().length() - 1);
        stringBuilder1.deleteCharAt(stringBuilder1.toString().length() - 1);

        String sql = "INSERT INTO car_shop_schema.%s(%s) VALUES (%s)"
                .formatted( nameOfTable, stringBuilder1, stringBuilder2);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].getType().isPrimitive()) {
                    preparedStatement.setInt(i, (int) fields[i].get(carShopEntity));
                } else {
                    preparedStatement.setString(i, fields[i].get(carShopEntity).toString());
                }
            }
            preparedStatement.executeUpdate();
            return true;
        } catch (PSQLException e1) {
                System.out.println("Уже существует!");
        } catch (SQLException | IllegalAccessException e2) {
            e2.getStackTrace();
        }
        return false;
    }
}
