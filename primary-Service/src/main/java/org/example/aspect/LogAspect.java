package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.util.aspectEntity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@Aspect
public class LogAspect {

    @Autowired
    private RestTemplate restTemplate;

    @Around("within(org.example.controller..*)")
    public Object saveLog(ProceedingJoinPoint joinPoint) {

        long timeBefore = System.nanoTime();
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long timeAfter = System.nanoTime();

        Log build = Log.builder().returnValue(proceed).args(Arrays.toString(joinPoint.getArgs())).workingMethod(String.valueOf(joinPoint.getSignature().getDeclaringType())).methodName(joinPoint.getSignature().getName()).runningTime(timeAfter - timeBefore).build();

        restTemplate.postForObject("http://"+ "MONGO" + "/api-mongo-log", build, Log.class);

        return proceed;
    }
}
