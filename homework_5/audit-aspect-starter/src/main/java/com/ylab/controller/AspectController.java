package com.ylab.controller;

import com.ylab.model.AspectEntry;
import com.ylab.service.AspectEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/carshop/")
@AllArgsConstructor
public class AspectController {

    private final AspectEntryService logEntryService;

    /**
     * Обработка запроса вывода логов (для администрации)
     * @return тело ответа
     */
    @Operation(summary = "Вывод логов (для администрации)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список логов")
    })
    @GetMapping("admin/logs")
    public ResponseEntity<List<AspectEntry>> getLogs() {
        return ResponseEntity.ok(logEntryService.getAllLogs());
    }
}
