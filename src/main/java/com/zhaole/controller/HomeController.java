package com.zhaole.controller;

import com.zhaole.model.Question;
import com.zhaole.model.ViewObject;
import com.zhaole.service.QuestionService;
import com.zhaole.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zl on 2019/1/24 19:58
 */

@Controller
public class HomeController
{
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    private List<ViewObject> getQuestions(int userId,int offset, int limit)
    {
        List<Question> questionList = questionService.getLatestQuestions(userId,offset,limit);
        List<ViewObject> viewObjectList = new ArrayList<>();
        for(Question question:questionList)
        {
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            viewObjectList.add(vo);
        }
        return viewObjectList;
    }

    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop",defaultValue = "0" ) int pop)
    {
        model.addAttribute("viewObjectList",getQuestions(0,0,10));
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String userIndex(Model model,
                            @PathVariable("userId") int userId)
    {
        model.addAttribute("viewObjectList",getQuestions(userId,0,10));
        return "index";
    }

}
