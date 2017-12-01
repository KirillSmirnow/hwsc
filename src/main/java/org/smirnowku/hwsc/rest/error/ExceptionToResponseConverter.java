package org.smirnowku.hwsc.rest.error;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionToResponseConverter {

    @Around("execution(org.springframework.http.ResponseEntity org.smirnowku.hwsc.rest.controller.*.*(..))")
    public ResponseEntity convert(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (ResponseEntity) joinPoint.proceed();
        } catch (BaseException e) {
            ErrorResponse response = new ErrorResponse(e.getHttpStatus(), e.getMessage(), e.getDeveloperMessage());
            return new ResponseEntity<>(response, e.getHttpStatus());
        }
    }
}
