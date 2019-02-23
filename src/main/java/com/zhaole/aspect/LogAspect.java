package com.zhaole.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.util.Date;

/**
 * created by zl on 2019/1/24 16:04
 */

@Aspect
@Component
public class LogAspect
{
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    //* 返回值。类。方法。（参数）
    @Before("execution(* com.zhaole.controller.HomeController.*(..))")
    public void beforeMethod(JoinPoint joinPoint)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(Object object: joinPoint.getArgs())
        {
            if(object != null)
            {
                stringBuilder.append("args:" + object.toString() +"|");
            }
        }
        logger.info("before method:" + stringBuilder.toString());
    }

    @After("execution(* com.zhaole.controller.HomeController.*(..))")
    public void afterMethod()
    {
        logger.info("after method " + new Date());
    }
}
