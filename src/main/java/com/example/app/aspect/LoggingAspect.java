package com.example.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Before("@annotation(com.example.app.aspect.annotation.LoggingPointcut)")
    public void logBefore(JoinPoint joinPoint){
        log.info("***********************************");
        log.info("Method : " + joinPoint.getSignature().getName());
        log.info("args : " + Arrays.stream(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(", ")));
        log.info("***********************************");
    }

    @Around("@annotation(com.example.app.aspect.annotation.LoggingPointcut)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint){
        log.info("***********************************");
        log.info("Method : " + proceedingJoinPoint.getSignature().getName());
        log.info("args : " + Arrays.stream(proceedingJoinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining(", ")));
        log.info("***********************************");

        Object obj = null;

        try {
            obj = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return obj;

    }

}
