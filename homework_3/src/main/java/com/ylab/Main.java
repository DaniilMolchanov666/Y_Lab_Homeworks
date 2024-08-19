package com.ylab;

import com.ylab.out.LiquibaseConfig;
import org.apache.catalina.LifecycleException;

/**
 * Класс для запуска приложения
 */
public class Main {

    public static void main(String[] args) throws LifecycleException {
        LiquibaseConfig.getConnectionWithLiquiBase();
    }
}
