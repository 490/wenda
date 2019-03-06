package com.zhaole.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhaole.async.EventHandler;
import com.zhaole.async.EventModel;
import com.zhaole.async.EventType;
import com.zhaole.model.EntityType;
import com.zhaole.model.Feed;
import com.zhaole.model.Question;
import com.zhaole.model.User;
import com.zhaole.service.FeedService;
import com.zhaole.service.FollowService;
import com.zhaole.service.QuestionService;
import com.zhaole.service.UserService;
import com.zhaole.service.impl.UserServiceImpl;
import com.zhaole.util.JedisAdapter;
import com.zhaole.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * created by zl on 2019/2/17 15:34
 */
@Component
public class FeedHandler implements EventHandler
{
    /*
    评论后立即发出一条新鲜事
    * */
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    FeedService feedService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    QuestionService questionService;
    private static final Logger logger = LoggerFactory.getLogger(FeedHandler.class);

    private String buildFeedData(EventModel eventModel)
    {
        Map<String, String> map = new HashMap<String, String>();
        //触发用户是通用的
        User actor = userService.getUser(eventModel.getActorId());
        logger.info("FeedHandler.build: actorid=="+ actor.getId());
        if(actor == null)
        {
            return null;
        }
        map.put("userId",String.valueOf(actor.getId()));
        map.put("userHead",actor.getHeadUrl());
        map.put("userName",actor.getName());

        //事件类型是评论，或者类型是关注问题
        if(eventModel.getType() == EventType.COMMENT ||
                (eventModel.getType() == EventType.FOLLOW && eventModel.getEntityType() == EntityType.ENTITY_QUESTION))
        {
            Question question = questionService.getById(eventModel.getEntityId());
            if(question == null)
            {
                return null;
            }
            map.put("questionId",String.valueOf(question.getId()));
            map.put("questionType",question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel eventModel)
    {
        /*方便测试把ActorId设置为随机值
        Random r = new Random();
        model.setActorId(1+ r.nextInt(10));     //随机生成1-10之间的值*/
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setUserId(eventModel.getActorId());
        feed.setType(eventModel.getType().getValue());
        feed.setData(buildFeedData(eventModel));//例如：谁评论了什么问题
        if(feed.getData() == null)
        {
            return;
        }
        feedService.addFeeds(feed);

        List<Integer> followers = followService.getFollowers(eventModel.getActorId(), EntityType.ENTITY_USER,Integer.MAX_VALUE);
        followers.add(0);
        for(int follower:followers)
        {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(new EventType[]{EventType.COMMENT,EventType.FOLLOW});
    }
}
