package com.zhaole.controller;


import com.zhaole.model.*;
import com.zhaole.service.*;
import com.zhaole.util.WendaUtil;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController
{
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Autowired
    FollowService followService;

    @RequestMapping(value = "/question/{qid}",method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid)
    {
        logger.info("↓↓↓↓↓↓------questionController.questionDetail()----------");

        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        model.addAttribute("user", userService.getUser(question.getUserId()));

        List<Comment> commentList = commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments = new ArrayList<ViewObject>();

        for (Comment comment : commentList)
        {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            //判断是否是我喜欢的
            if (hostHolder.getUser() == null)
            {
                vo.set("liked", 0);
            } else {
                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }

            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));

            vo.set("user", userService.getUser(comment.getUserId()));
            comments.add(vo);
        }

        model.addAttribute("comments", comments);

        //获取关注的问题信息
        List<Integer> users = followService.getFollowers(qid, EntityType.ENTITY_QUESTION, 20);
        List<ViewObject> followers = new ArrayList<>();
        for (Integer userId : users)
        {
            ViewObject vo = new ViewObject();
            User user = userService.getUser(userId);
            if (user == null)
            {
                continue;
            }
            vo.set("name", user.getName());
            vo.set("head_url", user.getHeadUrl());
            vo.set("id", user.getId());
            followers.add(vo);
        }
        model.addAttribute("followUsers", followers);
        if (hostHolder.getUser() != null)
        {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(), qid, EntityType.ENTITY_QUESTION));

        } else {
            model.addAttribute("followed", false);
        }
        logger.info("↑↑↑↑↑↑------questionController.questionDetail()----------");
        return "detail";
    }

    @RequestMapping(value = "/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content)
    {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            if(hostHolder.getUser() == null)
            {
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }else{
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question) > 0)
            {
                return WendaUtil.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("增加题目失败" + e.getMessage());
        }

        return WendaUtil.getJSONString(1,"失败");
    }

}
