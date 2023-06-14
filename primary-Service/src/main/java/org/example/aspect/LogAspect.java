package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.entity.aspectEntity.Log;
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

    @Value("${rest.template.url}")
    private String url;

    @Around("within(org.example.controller..*)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        long timeBefore = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long timeAfter = System.nanoTime();

        Log build = Log.builder()
                .returnValue(proceed)
                .args(Arrays.toString(joinPoint.getArgs()))
                .workingMethod(String.valueOf(joinPoint.getSignature().getDeclaringType()))
                .methodName(joinPoint.getSignature().getName())
                .runningTime(timeAfter - timeBefore)
                .build();

        restTemplate.postForObject(url + "/api-mongo-log", build, Log.class);

        return proceed;

    }
}
