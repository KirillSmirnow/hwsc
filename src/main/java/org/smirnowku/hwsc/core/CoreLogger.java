package org.smirnowku.hwsc.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class CoreLogger {

    private static final Logger log = LoggerFactory.getLogger(CoreLogger.class);

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logInvoke(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        String args = String.join(", ", Arrays.stream(joinPoint.getArgs())
                .map(arg -> arg == null ? "NULL" : arg.toString())
                .collect(Collectors.toList()));
        log.info(method.replace("..", args));
    }

    @AfterReturning(value = "serviceMethods()", returning = "object")
    public void logReturn(Object object) {
        log.info(String.format("returning: %s", object));
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "e")
    public void logThrow(Exception e) {
        log.info(String.format("throwing: %s", e.getMessage()));
    }
}
