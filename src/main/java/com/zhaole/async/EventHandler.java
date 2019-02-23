package com.zhaole.async;

import java.util.List;


public interface EventHandler
{
    void doHandle(EventModel model);
    //注册自己，让别人知道我关注哪些event
    List<EventType> getSupportEventTypes();
}
