package hwsc;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Slf4j
@Aspect
@Component
public class ServiceLogger {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logInvoke(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        String args = stream(joinPoint.getArgs())
                .map(arg -> arg == null ? "<NULL>" : "'" + arg.toString() + "'")
                .collect(joining(", "));
        log.info(method.replace("..", args));
    }

    @AfterReturning(value = "serviceMethods()", returning = "object")
    public void logReturn(Object object) {
        if (object != null) {
            String returnValue = object.toString();
            if (returnValue.length() > 200) {
                returnValue = returnValue.substring(0, 200);
            }
            log.info("return: " + returnValue);
        }
    }
}
