package com.zhaole.async;

import com.alibaba.fastjson.JSONObject;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by nowcoder on 2016/7/30.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            //可以用blockingQueue
            //BlockingQueue<EventModel> q = new ArrayBlockingQueue<EventModel>(10);
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();//STRING EVENT_QUEUE
            jedisAdapter.lpush(key, json);//把model放在list里，list名字是event-queue
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
