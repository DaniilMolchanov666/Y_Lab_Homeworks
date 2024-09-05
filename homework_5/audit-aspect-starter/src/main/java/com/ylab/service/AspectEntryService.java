package com.ylab.service;

import com.ylab.model.AspectEntry;
import com.ylab.repository.AspectEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Класс управляет добавлением и выводом логов
 */
@Service
@RequiredArgsConstructor
public class AspectEntryService {

    private final AspectEntryRepository logEntryRepository;

    public void save(AspectEntry entry) {
        logEntryRepository.save(entry);
    }

    public List<AspectEntry> getAllLogs() {
        return logEntryRepository.findAll();
    }

    public Optional<AspectEntry> findById(Integer id) {
        return logEntryRepository.findById(id);
    }
}

