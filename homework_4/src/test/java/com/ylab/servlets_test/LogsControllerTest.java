package com.ylab.servlets_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.controller.LogsController;
import com.ylab.entity.LogEntry;
import com.ylab.service.LogEntryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogsController.class)
public class LogsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogEntryService logEntryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetLogs() throws Exception {
        // Arrange
        List<LogEntry> logs = List.of(new LogEntry(), new LogEntry());
        when(logEntryService.getAllLogs()).thenReturn(logs);

        // Act & Assert
        mockMvc.perform(get("/v1/carshop/admin/show_logs"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(logs)));
    }
}
