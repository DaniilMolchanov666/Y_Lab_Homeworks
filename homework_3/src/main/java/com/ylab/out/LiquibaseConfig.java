package com.ylab.out;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Утилитарный класс для подключения к БД и создания таблиц через Liquibase
 */
public class LiquibaseConfig {

    public static Connection dbConnection;

    /**
     * Подключение к базе данных и выполнение changelog файлов
     */
    public static void getConnectionWithLiquiBase()  {

        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("/home/daniilmolchanov/Рабочий стол/apache-tomcat-10.1.28/webapps/carshop/WEB-INF/classes/properties/liquibase.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Не найден файл liquibase.properties");
        }

        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String changeLogFile = properties.getProperty("changeLogFile");

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Database database =
                    DatabaseFactory.getInstance()
                            .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
            dbConnection = connection;
        } catch (SQLException | LiquibaseException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
