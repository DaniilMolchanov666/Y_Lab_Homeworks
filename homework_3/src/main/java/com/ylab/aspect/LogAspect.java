//package com.ylab.aspect;
//
//import com.ylab.utils.AuditLogger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//
//@Aspect
//public class LogAspect {
//
//    @Pointcut("within(@com.ylab.annotation.Logging *) && execution(* *(..))")
//    public void annotatedLogging() {}
//
//    @Around("annotatedLogging()")
//    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//
//        long start =  System.currentTimeMillis();
//        Object result = proceedingJoinPoint.proceed();
//        long end = System.currentTimeMillis();
//
//        AuditLogger.getInstance().exportLogToFile("\nDuration: " + (end - start) + " ms");
//
//        return result;
//    }
//}
