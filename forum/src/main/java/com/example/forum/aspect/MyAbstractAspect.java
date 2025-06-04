package com.example.forum.aspect;

import com.example.forum.annotation.MyAbstract;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MyAbstractAspect {

    @Before("@annotation(com.example.forum.annotation.MyAbstract)")
    public void beforeMethod() {
        System.out.println("До");
    }

    @After("@annotation(com.example.forum.annotation.MyAbstract)")
    public void afterMethod() {
        System.out.println("После");
    }

    @Around("@annotation(com.example.forum.annotation.MyAbstract)")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Вокруг - до вызова метода");
        Object result = joinPoint.proceed();
        System.out.println("Вокруг - после вызова метода");
        return result;
    }
}