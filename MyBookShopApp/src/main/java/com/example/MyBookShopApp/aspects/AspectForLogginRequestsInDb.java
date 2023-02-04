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

import java.util.List;
import java.util.stream.Collectors;


//аспект для Логирования Запросов в БД
@Aspect
@Component
public class AspectForLogginRequestsInDb {

    private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @Pointcut("execution(* com.example.MyBookShopApp.repositories..*(..))")
    public void requestsRepo() {
    }

    @Around(value = "requestsRepo()")
    public Object aroundRepoMethodAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.DEBUG);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);

        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        logger.detachAppender(listAppender);
        listAppender.stop();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.SQL")).setLevel(Level.INFO);

        List<String> sqlStatements = listAppender.list.stream()
                .filter(event -> event.getMessage().startsWith("select"))
                .map(ILoggingEvent::getMessage)
                .collect(Collectors.toList());
        String sqlQuery = sqlStatements.get(0);
        logger.info("SQL запрос: " + sqlQuery);

        return returnValue;
    }
}
