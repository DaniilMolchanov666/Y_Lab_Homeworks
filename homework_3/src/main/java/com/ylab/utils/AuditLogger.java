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

    private final String PATH_TO_FILE_FOR_LOGS = "/home/daniilmolchanov/Рабочий стол/Y_Lab_Homeworks/homework_3/src/main/resources/logs";

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
     * Экспортирует журнал действий в текстовый файл.
     */
    public void exportLogToFile(String info) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_FILE_FOR_LOGS, true))) {
            writer.write(new Date() + info + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка файла!");
        }
    }
}