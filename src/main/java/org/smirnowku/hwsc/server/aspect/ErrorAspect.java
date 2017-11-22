package org.smirnowku.hwsc.server.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.smirnowku.hwsc.server.exception.HwscException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ErrorAspect {

    @Around("execution(org.springframework.http.ResponseEntity org.smirnowku.hwsc.server.controller.*.*(..))")
    public ResponseEntity handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (ResponseEntity) joinPoint.proceed();
        } catch (HwscException e) {
            return new ResponseEntity<>(e.errorResponse(), e.httpStatus());
        }
    }
}
