package com.ylab.repository;

import com.ylab.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    LogEntry save(LogEntry logEntry);
}
