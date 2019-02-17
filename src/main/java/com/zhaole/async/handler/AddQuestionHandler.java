package com.zhaole.async.handler;

import com.zhaole.async.EventHandler;
import com.zhaole.async.EventModel;
import com.zhaole.async.EventType;
import com.zhaole.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * created by zl on 2019/2/16 22:43
 */
@Component
public class AddQuestionHandler implements EventHandler
{
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);
    @Autowired
    SearchService searchService;

    @Override
    public void doHandle(EventModel eventModel)
    {
        try {
            searchService.indexQuestion(eventModel.getEntityId(), eventModel.getExt("title"), eventModel.getExt("content"));
        }catch (Exception e){
            logger.error("增加题目索引失败");
        }
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
