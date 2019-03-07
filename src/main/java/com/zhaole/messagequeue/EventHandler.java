package com.zhaole.messagequeue;

import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventType;

import java.util.List;


public interface EventHandler
{
    void doHandle(EventModel model);
    //注册自己，让别人知道我关注哪些event
    List<EventType> getSupportEventTypes();
}
