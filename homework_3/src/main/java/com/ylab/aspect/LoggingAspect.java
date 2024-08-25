package com.ylab.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Аспект для логирования и вывода длительности выполнения сервлета
 */
@Aspect
@Log4j2
public class LoggingAspect {

    @Around("execution(* com.ylab.servlet.*.*(..)) && args(request, response)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint, HttpServletRequest request, HttpServletResponse response) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;


        log.log(Level.INFO, "\nDuration: " + executionTime + " ms");
        log.log(Level.INFO, "\nRequest: " + request.getRequestURI());
        log.log(Level.INFO, "\nResponse: " + response.getStatus());

        return result;
    }
}
