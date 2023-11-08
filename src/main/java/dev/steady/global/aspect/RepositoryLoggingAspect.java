package dev.steady.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryLoggingAspect {

    @Around("this(org.springframework.data.repository.Repository)")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("> 핸들러 {} | 쿼리 {}  | 걸린 시간 {}ms", MDC.get("handler"), MDC.get("query"), (end - start));

        return returnValue;
    }

}
