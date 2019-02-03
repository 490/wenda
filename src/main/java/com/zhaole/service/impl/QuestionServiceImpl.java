package com.zhaole.service.impl;

import com.zhaole.dao.QuestionDAO;
import com.zhaole.dao.UserDAO;
import com.zhaole.model.Question;
import com.zhaole.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * created by zl on 2019/1/24 20:13
 */
@Service
public class QuestionServiceImpl implements QuestionService
{

    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    SensitiveService sensitiveService;

    public Question getById(int id)
    {
        return questionDAO.getById(id);
    }

    //先传进来一个question，然后重新设置其title和content
    public int addQuestion(Question question)
    {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public List<Question> getLatestQuestions(int userId,int offset,int limit)
    {
        return questionDAO.selectLatestQuestions(userId,offset,limit);
    }

    public int updateCommentCount(int id,int count)
    {
        return questionDAO.updateCommentCount(id,count);
    }
}
