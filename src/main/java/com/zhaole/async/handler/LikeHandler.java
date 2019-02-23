package com.zhaole.async.handler;

import com.zhaole.async.EventHandler;
import com.zhaole.async.EventModel;
import com.zhaole.async.EventType;
import com.zhaole.model.Message;
import com.zhaole.model.User;
import com.zhaole.service.MessageService;
import com.zhaole.service.UserService;
import com.zhaole.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 */
@Component
public class LikeHandler implements EventHandler
{
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model)
    {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        message.setConversationId();
        message.setHasRead(0);
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.LIKE);
    }
}
