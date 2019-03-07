package com.zhaole.util;

import java.lang.annotation.*;

/**
 * created by zl on 2019/3/7 21:15
 */

@Inherited
@Documented
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit
{
    //默认每秒放入桶中的token
    double limitNum() default 50;
}
