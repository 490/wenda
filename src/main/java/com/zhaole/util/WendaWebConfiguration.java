package com.zhaole.util;

import com.zhaole.interceptor.LoginRequiredInterceptor;
import com.zhaole.interceptor.PasswordInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by zl on 2019/1/25 8:59
 */
//不需要调用，会自动执行
@Component
@Configuration
public class WendaWebConfiguration implements WebMvcConfigurer
{
    @Autowired
    PasswordInterceptor passwordInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(passwordInterceptor);
        //注意拦截器顺序，因为hosthold变量的定义和使用顺序。
        //在某些页面才需要第二个拦截器
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
      //  super.addInterceptors(registry);
    }
}
