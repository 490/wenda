package com.zhaole.messagequeue;

import com.alibaba.fastjson.JSONObject;
import com.zhaole.messagequeue.EventModel;
import com.zhaole.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by zl on 2019/3/7 15:38
 */
@Service("mq_EventProducer")
public class EventProducer
{
    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    AmqpTemplate amqpTemplate ;

    public boolean fireEvent(EventModel eventModel)
    {
        try {
            String json = JSONObject.toJSONString(eventModel);
            amqpTemplate.convertAndSend("messagequeue",json);
            logger.info("rabbitMQ: producer: " + json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
