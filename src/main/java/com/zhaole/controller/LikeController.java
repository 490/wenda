package com.zhaole.controller;

import com.zhaole.messagequeue.EventModel;
import com.zhaole.messagequeue.EventProducer;
import com.zhaole.messagequeue.EventType;
import com.zhaole.model.Comment;
import com.zhaole.model.EntityType;
import com.zhaole.model.HostHolder;
import com.zhaole.service.CommentService;
import com.zhaole.service.LikeService;
import com.zhaole.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LikeController
{
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    //Controller是一个事件生产者。
    @Autowired
    EventProducer eventProducer;
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId)
    {
        logger.info("--------likeController.like()----------");
        if (hostHolder.getUser() == null)
        {
            return WendaUtil.getJSONString(999);
        }

        Comment comment = commentService.getCommentById(commentId);

        eventProducer.fireEvent(//eventmodel放到一个redis 的list里
                new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()) //当前登录用户
                .setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT)
                .setEntityOwnerId(comment.getUserId())//写评论的人
                .setExt("questionId", String.valueOf(comment.getEntityId()))//Map<String, String>
        );
        //like返回的是redis的scard命令。<like:entitytype:entityid , userid>这个key的数量
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId)
    {
        if (hostHolder.getUser() == null)
        {
            return WendaUtil.getJSONString(999);
        }

        long likeCount = likeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return WendaUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
