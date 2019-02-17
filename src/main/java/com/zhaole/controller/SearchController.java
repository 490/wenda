package com.zhaole.controller;

import com.zhaole.model.EntityType;
import com.zhaole.model.Question;
import com.zhaole.model.ViewObject;
import com.zhaole.service.FollowService;
import com.zhaole.service.QuestionService;
import com.zhaole.service.SearchService;
import com.zhaole.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zl on 2019/2/17 20:15
 */
@Controller
public class SearchController
{
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/search"},method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("question") String keyword,
                         @RequestParam(value = "offset",defaultValue = "0") int offset,
                         @RequestParam(value = "count",defaultValue = "10") int count)
    {
        try {
            List<Question> questionList = searchService.searchQuestion(keyword, offset, count,"<em>", "</em>");
            List<ViewObject> viewObjectList = new ArrayList<>();
            for(Question question: questionList)
            {
                Question q = questionService.getById(question.getId());
                ViewObject viewObject = new ViewObject();
                if(question.getContent() != null)
                {
                    q.setContent(question.getContent());
                }
                if(question.getTitle() != null)
                {
                    q.setTitle(question.getTitle());
                }

                viewObject.set("question", q);
                viewObject.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                viewObject.set("user", userService.getUser(q.getUserId()));
                viewObjectList.add(viewObject);
            }
            model.addAttribute("vos",viewObjectList);
            model.addAttribute("keyword", keyword);
        }catch (Exception e){
            logger.error("搜索失败"+e.getMessage());
        }
        return "result";
    }
}
