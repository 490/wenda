package com.zhaole.messagequeue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by zl on 2019/3/7 16:51
 */
@Configuration
public class MessageQueueConfig
{
    @Bean
    public Queue queue() {
        return new Queue("messagequeue", true);
    }
}
