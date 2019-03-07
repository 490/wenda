package com.zhaole.messagequeue;

import com.alibaba.fastjson.JSON;
import com.zhaole.messagequeue.EventHandler;
import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by zl on 2019/3/7 15:47
 */
@Service("mq_EventConsumer")
public class EventConsumer implements InitializingBean, ApplicationContextAware
{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> registeredHandler = new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        //通过spring的上下文可以知道有多少个EventHandler接口的实现类(从容器里找就不需要用配置文件等等，在初始化的时候就会注册好）
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null)
        {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet())
            {
                //得到这个eventHandler实现类的eventType
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                for (EventType type : eventTypes)
                {
                    if (!registeredHandler.containsKey(type))
                    {
                        registeredHandler.put(type, new ArrayList<EventHandler>());
                    }
                    //每个类型对应一个list，向list里添加value，即eventHandler。
                    registeredHandler.get(type).add(entry.getValue());
                }
            }
        }
    }


    @RabbitListener(queues="messagequeue")
    public void consume(String event)
    {
        logger.info("RabbitMQ:consumer: "+ event);
        EventModel eventModel = JSON.parseObject(event, EventModel.class);
        if (!registeredHandler.containsKey(eventModel.getType()))
        {
            logger.error("不能识别的事件"+eventModel.getType());
        }

        for (EventHandler handler : registeredHandler.get(eventModel.getType()))
        {
            handler.doHandle(eventModel);
        }


    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

}
