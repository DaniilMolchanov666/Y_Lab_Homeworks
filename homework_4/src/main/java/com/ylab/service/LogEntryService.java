package com.ylab.service;

import com.ylab.entity.LogEntry;
import com.ylab.repository.LogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс управляет добавлением и выводом логов
 */
@Service
@RequiredArgsConstructor
public class LogEntryService {

    private final LogEntryRepository logEntryRepository;

    public void save(LogEntry logEntry) {
        logEntryRepository.save(logEntry);
    }

    public List<LogEntry> getAllLogs() {
        return logEntryRepository.findAll();
    }
}
