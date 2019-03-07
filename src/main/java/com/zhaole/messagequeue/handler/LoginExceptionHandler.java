package com.zhaole.messagequeue.handler;

import com.zhaole.messagequeue.EventHandler;
import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventType;
import com.zhaole.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("mq_LoginExceptionHandler")
public class LoginExceptionHandler implements EventHandler
{
    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model)
    {
        // xxxx判断发现这个用户登陆异常
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆IP异常", "mails/login_exception.html");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
