package com.zhaole.messagequeue.handler;

import com.zhaole.controller.CommentController;
import com.zhaole.messagequeue.EventHandler;
import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * created by zl on 2019/3/7 16:24
 */
@Component("mq_CommentHandler")
public class CommentHandler  implements EventHandler
{
    private static final Logger logger = LoggerFactory.getLogger(CommentHandler.class);

    @Override
    public void doHandle(EventModel model)
    {
        logger.info("rabbitMQ....success...commentHandler");
    }
    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.COMMENT);
    }
}
