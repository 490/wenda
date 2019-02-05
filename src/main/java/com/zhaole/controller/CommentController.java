package com.zhaole.controller;

import com.zhaole.model.Comment;
import com.zhaole.model.EntityType;
import com.zhaole.model.HostHolder;
import com.zhaole.service.CommentService;
import com.zhaole.service.QuestionService;
import com.zhaole.util.WendaUtil;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/addCommnet"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content)
    {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if(hostHolder.getUser() != null)
            {
                comment.setUserId(hostHolder.getUser().getId());
            }else{
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }

            comment.setCreatedDate(new Date());
            comment.setEntityId(questionId);
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            commentService.addComment(comment);
            //在这个questionid，及类型是question的数量，comment表里
            int count = commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);
        }catch (Exception e){
            logger.error("增加评论失败"+e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }


}
