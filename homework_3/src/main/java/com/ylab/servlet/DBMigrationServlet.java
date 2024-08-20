package com.ylab.servlet;

import com.ylab.out.LiquibaseConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DBMigrationServlet implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LiquibaseConfig.getConnectionWithLiquiBase();
    }
}
