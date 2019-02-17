package com.zhaole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * created by zl on 2019/1/24 15:56
 */
@SpringBootApplication
public class WendaApplication  extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
    {
        return builder.sources(WendaApplication.class);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(WendaApplication.class, args);
    }
}
