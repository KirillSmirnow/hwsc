package org.smirnowku.hwsc.server.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = Logger.getLogger("Main Log");

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logInvoke(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        String args = String.join(", ", Arrays.stream(joinPoint.getArgs())
                .map(arg -> arg == null ? "NULL" : arg.toString())
                .collect(Collectors.toList()));
        logger.info(method.replace("..", args));
    }

    @AfterReturning(value = "serviceMethods()", returning = "object")
    public void logReturn(Object object) {
        logger.info(String.format("returning: %s", object));
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "e")
    public void logThrow(Exception e) {
        logger.info(String.format("throwing: %s", e.getMessage()));
    }
}
