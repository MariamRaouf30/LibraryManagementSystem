package com.example.LibrarySystem.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.example.LibrarySystem.controller.*.*(..))")
    public void controllerMethods() {}

    @Before("controllerMethods()")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Method Call: " + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in method " + joinPoint.getSignature().getName() + ": " + ex.getMessage());
    }

    @Around("controllerMethods()")
    public Object logPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        logger.info("Execution time of " + joinPoint.getSignature().getName() + " : " + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }
}