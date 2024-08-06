package com.ylab.controller;

import com.ylab.entity.Role;
import com.ylab.entity.User;
import com.ylab.service.AuthorizationService;
import com.ylab.utils.AuditLogger;

import java.util.List;

import static java.lang.System.out;

/**
 * Контроллер для обработки запросов, связанных с логированием действий пользователей
 */
public class AuditController {

    private final AuditLogger auditLogger = AuditLogger.getInstance();

    private final AuthorizationService authorizationService;

    /**
     * Конструктор, где происходит внедрение нужных зависимостей
     *
     * @param authorizationService сервис авторизации
     */
    public AuditController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * Обработка запросов проверки доступа пользователя, выгрузки логов действий пользователей,
     * а также записи всех логов в файл 'logs' в папке resources
     */
    public void viewAuditLog(User currentUser) {
        if (!authorizationService.isAuthorized(currentUser, Role.ADMIN)) {
            out.println("У вас нет прав для просмотра журнала действий!");
            return;
        }
        List<String> log = auditLogger.getListOfLogs();
        if (log.isEmpty()) {
            out.println("Журнал действий пуст!");
        } else {
            for (String entry : log) {
                out.println(entry);
            }
            auditLogger.exportLogToFile();
        }
    }
}
