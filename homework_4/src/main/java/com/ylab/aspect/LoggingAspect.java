package com.ylab.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.config.LoggingUtils;
import com.ylab.entity.LogEntry;
import jakarta.servlet.http.HttpServletRequest;
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
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterReturning(value = "execution(* com.ylab.controller.*.*(..))", returning = "result")
    public void logControllerMethodCall(JoinPoint joinPoint, Object result) throws JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();

        LogEntry logEntry = new LogEntry();
        logEntry.setEndPoint(objectMapper.writeValueAsString(request.getRequestURI()));
        logEntry.setMessage(result.toString());
        logEntry.setCreatedAt(String.valueOf(LocalDateTime.now()));

        LoggingUtils.saveLog((logEntry));
    }
}
