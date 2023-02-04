package com.example.MyBookShopApp.aspects;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


//аспект для Логирования Запросов в БД
@Aspect
@Component
public class AspectForDuration {

    private Long durationMills;
    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());


    @Around(value = "@annotation(com.example.MyBookShopApp.annotation.DurationTrackable))")
    public Object aroundDurationTrackingAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        durationMills = new Date().getTime();
        logger.info(proceedingJoinPoint.toString() + " duration tracking begins");
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        durationMills = new Date().getTime() - durationMills;
        logger.info(proceedingJoinPoint.toString() + " generateUuid took: " + durationMills + " mills");
        return returnValue;
    }
}
