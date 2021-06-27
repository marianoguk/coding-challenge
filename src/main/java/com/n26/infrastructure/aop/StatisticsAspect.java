package com.n26.infrastructure.aop;

import com.n26.domain.dto.TransactionDto;
import com.n26.domain.service.StatisticsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticsAspect {

    @Autowired
    private StatisticsService service;

    @Around("@annotation(com.n26.infrastructure.aop.TrackedEvent)")
    public Object processEvent(ProceedingJoinPoint joinPoint) throws Throwable {

        try{
            Object process = joinPoint.proceed();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            TrackedEvent annotation = signature.getMethod().getAnnotation(TrackedEvent.class);
            if (annotation.operation() == TrackedEventType.NEW) {
                service.process((TransactionDto) joinPoint.getArgs()[0]);
            }
            if(annotation.operation() == TrackedEventType.DELETE_ALL) {
                service.deleteAll();
            }
            return process;
        } catch (Exception e) {
            throw e;
        }

    }
}
