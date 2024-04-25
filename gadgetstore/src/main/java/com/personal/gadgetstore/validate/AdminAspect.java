package com.personal.gadgetstore.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AdminAspect {

    @Around("@annotation(Admin)")
    public Object checkAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        for (Object arg : args) {
            if (arg instanceof String && "PwdOfAdminS".equals(arg)) {
                return joinPoint.proceed();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Unauthorized access");
    }
}
