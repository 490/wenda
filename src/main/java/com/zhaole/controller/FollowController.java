package com.zhaole.controller;

import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventProducer;
import com.zhaole.messagequeue.EventType;
import com.zhaole.model.*;
import com.zhaole.service.CommentService;
import com.zhaole.service.FollowService;
import com.zhaole.service.QuestionService;
import com.zhaole.service.UserService;
import com.zhaole.service.impl.UserServiceImpl;
import com.zhaole.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by zl on 2019/2/16 19:39
 */
@Controller
public class FollowController
{
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

    @RequestMapping(path = {"/followUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId)
    {
        if(hostHolder == null)
        {
            return WendaUtil.getJSONString(999);
        }
        //返回是否关注成功
        boolean ret = followService.follow(hostHolder.getUser().getId(),userId,EntityType.ENTITY_USER);
        logger.info("followController.followUser:"+ret);
        //jedisAdapter.lpush(key, toJSONString(eventModel))
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                                    .setActorId(hostHolder.getUser().getId())
                                    .setEntityId(userId)
                                    .setEntityType(EntityType.ENTITY_USER)
                                    .setEntityOwnerId(userId));
        //返回我关注了多少人
        return WendaUtil.getJSONString(ret ? 0 : 1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_USER)));
    }

    @RequestMapping(path = {"/unfollowUser"},method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId)
    {
        if(hostHolder == null)
        {
            return WendaUtil.getJSONString(999);
        }
        //是否关注
        boolean ret = followService.unfollow(hostHolder.getUser().getId(),userId,EntityType.ENTITY_USER);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                                    .setActorId(hostHolder.getUser().getId())
                                    .setEntityId(userId)
                                    .setEntityType(EntityType.ENTITY_USER)
                                    .setEntityOwnerId(userId));
        return WendaUtil.getJSONString(ret ? 0 : 1,String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_USER)));
    }

    //关注问题
    @RequestMapping(path = {"/followQuestion"},method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId)
    {
        if(hostHolder == null)
        {
            return WendaUtil.getJSONString(999);
        }

        Question question = questionService.getById(questionId);
        if(question == null)
        {
            return WendaUtil.getJSONString(1,"问题不存在");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(),questionId,EntityType.ENTITY_QUESTION);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                                    .setActorId(hostHolder.getUser().getId())
                                    .setEntityId(questionId)
                                    .setEntityType(EntityType.ENTITY_QUESTION)
                                    .setEntityOwnerId(questionId));

        //与前端交互的信息
        Map<String, Object> info = new HashMap<>();
        info.put("headURL", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(questionId, EntityType.ENTITY_QUESTION));

        //返回我关注的问题
        return WendaUtil.getJSONString(ret ? 0 : 1,info);
    }

    //取消关注问题
    @RequestMapping(path = {"/unfollowQuestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId){
        if (hostHolder.getUser() == null){
            return WendaUtil.getJSONString(999);
        }

        Question question = questionService.getById(questionId);
        if (question == null){
            return WendaUtil.getJSONString(1, "问题不存在");
        }
        //是否关注
        boolean ret = followService.unfollow(hostHolder.getUser().getId(), questionId, EntityType.ENTITY_QUESTION);
        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                                    .setActorId(hostHolder.getUser().getId())
                                    .setEntityId(questionId)
                                    .setEntityType(EntityType.ENTITY_QUESTION)
                                    .setEntityOwnerId(question.getUserId()));

        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowerCount(questionId, EntityType.ENTITY_QUESTION));

        return WendaUtil.getJSONString(ret ? 0 : 1, info);  //我当前关注了多少人
    }

    private List<ViewObject> getUserInfo(int localUserId, List<Integer> userIds)
    {
        List<ViewObject> userInfos = new ArrayList<ViewObject>();
        for(Integer uid:userIds)
        {
            User user = userService.getUser(uid);
            if(user == null)
            {
                continue;
            }
            ViewObject viewObject = new ViewObject();
            viewObject.set("user",user);
            viewObject.set("commentCount",commentService.getUserCommentCount(uid));
            viewObject.set("followerCount",followService.getFollowerCount(uid,EntityType.ENTITY_USER));
            viewObject.set("followeeCount",followService.getFolloweeCount(uid,EntityType.ENTITY_USER));
            if(localUserId != 0)
            {
                //判断localUserId是不是uid的粉丝，localuser是不是关注了uid。
                viewObject.set("followed", followService.isFollower(localUserId,uid, EntityType.ENTITY_USER));
            }else {
                viewObject.set("followed",false);
            }
            userInfos.add(viewObject);
        }
        return userInfos;
    }

    //关注的人有哪些
    @RequestMapping(path = {"/user/{uid}/followees"},method = {RequestMethod.GET})
    public String followees(Model model, @PathVariable("uid") int userId)
    {
        //分页显示关注人列表
        List<Integer> followeesIds = followService.getFollowees(userId, EntityType.ENTITY_USER, 0, 10);
        if(hostHolder.getUser() != null)
        {
            model.addAttribute("followees", getUserInfo(hostHolder.getUser().getId(),followeesIds));
        }else{
            model.addAttribute("followees",getUserInfo(0,followeesIds));
        }
        model.addAttribute("followeeCount",followService.getFolloweeCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser",userService.getUser(userId));
        return "followees";
    }

    //粉丝
    @RequestMapping(path = {"/user/{uid}/followers"},method = {RequestMethod.GET})
    public String followers(Model model, @PathVariable("uid") int userId)
    {
        //分页显示关注人列表
        List<Integer> followersIds = followService.getFollowers(userId, EntityType.ENTITY_USER, 0, 10);
        if(hostHolder.getUser() != null)
        {
            model.addAttribute("followers", getUserInfo(hostHolder.getUser().getId(),followersIds));
        }else{
            model.addAttribute("followers",getUserInfo(0,followersIds));
        }
        model.addAttribute("followerCount",followService.getFollowerCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("curUser",userService.getUser(userId));
        return "followers";
    }
}
