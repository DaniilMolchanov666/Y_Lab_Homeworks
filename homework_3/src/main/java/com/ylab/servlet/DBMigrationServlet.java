package com.ylab.servlet;

import com.ylab.out.LiquibaseConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Класс для задания подключения к БД перед выполнением всех сервлетов
 */
@WebListener
public class DBMigrationServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseConfig.getConnectionWithLiquiBase();
    }
}
