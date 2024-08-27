package com.ylab.controller;

import com.ylab.entity.LogEntry;
import com.ylab.service.LogEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов получения логов
 */
@RestController
@RequestMapping("/v1/carshop/")
@AllArgsConstructor
public class LogsController {

    private final LogEntryService logEntryService;

    /**
     * Обработка запроса вывода логов (для администрации)
     * @return тело ответа
     */
    @Operation(summary = "Вывод логов (для администрации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список логов")
    })
    @GetMapping("admin/show_logs")
    public ResponseEntity<List<LogEntry>> getLogs() {
        return ResponseEntity.ok(logEntryService.getAllLogs());
    }
}
