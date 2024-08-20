package com.ylab.aspect;

import com.ylab.utils.AuditLogger;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {

    private final AuditLogger auditLogger = AuditLogger.getInstance();

    @AfterReturning(pointcut = "execution(* com.example.servlet.*.*(..)) && args(..,resp)")
    public void logResponse(JoinPoint joinPoint, Object result, HttpServletResponse response) {
        auditLogger.exportLogToFile(joinPoint.getSignature().getName());
        auditLogger.exportLogToFile(String.valueOf(response.getStatus()));
    }
}
