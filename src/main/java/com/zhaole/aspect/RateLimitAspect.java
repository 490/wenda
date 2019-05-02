package com.zhaole.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * created by zl on 2019/3/7 21:16
 */
@Component
@Scope
@Aspect
public class RateLimitAspect
{
    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);

    //每秒只发出5个令牌，此处是单进程服务的限流,内部采用令牌捅算法实现
    private static   RateLimiter rateLimiter = RateLimiter.create(5.0);

    //Service层切点  限流。Pointcut 是指那些方法需要被执行"AOP",是由"Pointcut Expression"来描述的.
    @Pointcut("@annotation(com.zhaole.util.RateLimit)")
    public void ServiceAspect()//切点签名
    {

    }
    ///当需要改变目标方法的返回值时，只能使用Around方法；
    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint)
    {
        Boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if(flag)
            {
                obj = joinPoint.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }
}
