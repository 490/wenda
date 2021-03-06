package com.zhaole.async;

import com.alibaba.fastjson.JSON;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware
{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<EventType, List<EventHandler>>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

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
                    if (!config.containsKey(type))
                    {
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    //每个类型对应一个list，向list里添加value，即eventHandler。
                    config.get(type).add(entry.getValue());
                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            //启动后，开启一个线程循环的去查queue里有没有event，如果有就调用List<EventHandler>一个一个处理
            public void run()
            {
                while(true)
                {
                    String key = RedisKeyUtil.getEventQueueKey();//EVENT_QUEUE
                    /*Redis Brpop 命令移出并获取列表的最后一个元素， 
                    如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
                    假如在指定时间内没有任何元素被弹出，则返回一个 nil 和等待时长。
                    反之，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值。
                    */
                    List<String> events = jedisAdapter.brpop(0, key);

                    for (String message : events)
                    {
                        if (message.equals(key))
                        {
                            continue;
                        }
                        //反序列化
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);
                        if (!config.containsKey(eventModel.getType()))
                        {
                            logger.error("不能识别的事件"+eventModel.getType());
                            continue;
                        }

                        for (EventHandler handler : config.get(eventModel.getType()))
                        {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
