package com.ylab.config;

import com.ylab.entity.LogEntry;
import com.ylab.service.LogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Вспомогательный утилитарный класс для внедрения зависимостей, связанных с логами, для использования в аспекте
 */
@Component
public class LoggingUtils {

    private static LogEntryService loggingService;

    /**
     * Внедрение LoggingService через сеттер
     */
    @Autowired
    public void setLoggingService(LogEntryService loggingService) {
        LoggingUtils.loggingService = loggingService;
    }

    /**
     * Добавление логов в базу данных
     */
    public static void saveLog(LogEntry logEntry) {
        loggingService.save(logEntry);
    }
}