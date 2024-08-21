package com.ylab.aspect;

import com.ylab.utils.AuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Аспект для логирования и вывода длительности выполнения сервлета
 */
@Aspect
public class LoggingAspect {

    private AuditLogger auditLogger = AuditLogger.getInstance();

    @Around("execution(* com.ylab.servlet.*.*(..)) && args(request, response)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        auditLogger.exportLogToFile("\nDuration: " + executionTime + " ms");
        auditLogger.exportLogToFile("\nRequest: " + request.getRequestURI());
        auditLogger.exportLogToFile("\nResponse: " + response.getStatus());

        return result;
    }
}
