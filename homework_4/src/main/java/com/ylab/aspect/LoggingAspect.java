package com.ylab.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.entity.LogEntry;
import com.ylab.service.LogEntryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * Аспект для логирования и вывода длительности выполнения сервлета
 */
@Aspect
@Component
@Setter
public class LoggingAspect {

    private LogEntryService logEntryService;

    private ObjectMapper objectMapper;

    @AfterReturning(value = "execution(* com.ylab.controller.*.*(..))", returning = "result")
    public void logControllerMethodCall(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();

        LogEntry logEntry = new LogEntry();
        logEntry.setEndPoint(request.getRequestURI());
        logEntry.setMessage(objectMapper.writeValueAsString(result));
        logEntry.setCreatedAt(LocalDateTime.now());

        logEntryService.save(logEntry);
    }
}
