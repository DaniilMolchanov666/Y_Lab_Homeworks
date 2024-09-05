package com.ylab.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * Аспект для отображения информации о длительности выполнения всех методов
 */
@Aspect
@Component
@Log4j2
public class LogAspect {

    @Pointcut("execution(* com.homework.controller..* (..))")
    public void anyControllerMethod() {}

    /**
     * Метод обрабатывает все методы
     * в случае успешного завершения метода выводит информации о времени работы обрабатываемого метода
     * если во время выполнения метода выбрасывается исключение, то метод пробрасывает исключение дальше
     * @param proceedingJoinPoint JoinPoint
     * @throws Throwable Пробрасывает исключение если его выбрасывает обрабатываемый метод
     */
    @Around("anyControllerMethod()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        log.info("Метод: %s Аргументы: %s Время выполнения: %s ms".formatted(
                proceedingJoinPoint.getSignature().getName(),
                Arrays.toString(proceedingJoinPoint.getArgs())),
                time
        );
        return result;
    }
}