package com.zhaole.messagequeue.handler;

import com.zhaole.controller.CommentController;
import com.zhaole.messagequeue.EventHandler;
import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventType;
import com.zhaole.model.Message;
import com.zhaole.model.User;
import com.zhaole.service.MessageService;
import com.zhaole.service.UserService;
import com.zhaole.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * created by zl on 2019/3/7 16:24
 */
@Component("mq_CommentHandler")
public class CommentHandler  implements EventHandler
{
    private static final Logger logger = LoggerFactory.getLogger(CommentHandler.class);
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model)
    {
        logger.info("rabbitMQ....success...commentHandler");
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "评论了你,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        message.setConversationId();
        message.setHasRead(0);
        messageService.addMessage(message);
    }
    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.COMMENT);
    }
}
