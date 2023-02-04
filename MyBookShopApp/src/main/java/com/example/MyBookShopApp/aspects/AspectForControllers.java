package com.example.MyBookShopApp.aspects;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


//аспект для Логирования Запросов в БД
@Aspect
@Component
public class AspectForControllers {

    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(this.getClass().getName());

    @Pointcut("execution(* com.example.MyBookShopApp.controllers..*(..))")
    public void requestsControllers() {
    }



    @After(value = "requestsControllers()")
    public void aroundRepoMethodAdvice(JoinPoint joinPoint) {
        logger.info("Controller method " + joinPoint.toShortString() + " was invoked");
    }
}
