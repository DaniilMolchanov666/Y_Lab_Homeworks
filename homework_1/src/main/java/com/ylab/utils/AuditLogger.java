package com.ylab.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс обеспечивает логирование действий пользователей.
 */
public class AuditLogger {

    private List<String> log = new ArrayList<>();

    /**
     * Логирует действие пользователя.
     *
     * @param action Действие для логирования.
     */
    public void logAction(String action) {
        log.add(action);
    }

    /**
     * Возвращает журнал действий пользователей.
     *
     * @return Журнал действий.
     */
    public List<String> getLog() {
        return new ArrayList<>(log);
    }

    public void exportLogToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            List<String> listOfLogs = log.stream().peek(i -> {
                try {
                    writer.write(new Date() + " - " + i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка файла!");
        }
    }
}