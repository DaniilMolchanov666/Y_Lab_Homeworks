package com.ylab.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс обеспечивает логирование действий пользователей
 */
public class AuditLogger {

    private static AuditLogger auditLogger;

    private List<String> listOfLogs = new ArrayList<>();

    private final String PATH_TO_FILE_FOR_LOGS = "./homework_1/src/main/resources/logs";

    /**
     * Метод, который создает один экземпляр класса для всего приложения
     * @return экземпляр данного класса
     */
    public static AuditLogger getInstance() {
        if (auditLogger == null) {
            auditLogger = new AuditLogger();
        }
        return auditLogger;
    }

    private AuditLogger() {}

    /**
     * Логирует действие пользователя.
     *
     * @param action Действие для логирования.
     */
    public void logAction(String action) {
        listOfLogs.add(action);
    }

    /**
     * Возвращает журнал действий пользователей.
     *
     * @return Журнал действий.
     */
    public List<String> getListOfLogs() {
        return new ArrayList<>(listOfLogs);
    }

    /**
     * Экспортирует журнал действий в текстовый файл.
     */
    public void exportLogToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_FILE_FOR_LOGS, true))) {
            String logs = listOfLogs.stream()
                    .map(i -> new Date() + i + "\n")
                    .collect(Collectors.joining());
            writer.write(logs);
        } catch (IOException e) {
            System.out.println("Ошибка файла!");
        }
    }
}