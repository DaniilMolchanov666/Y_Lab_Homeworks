package com.ylab;

import com.ylab.in.ConsoleInterface;
import com.ylab.out.LiquibaseConfig;

/**
 * Класс для запуска приложения
 */
public class Main {

    public static void main(String[] args) {
        LiquibaseConfig.getConnectionWithLiquiBase();
        new ConsoleInterface().startMenu();
    }
}
