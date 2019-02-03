package com.zhaole.service;

import com.zhaole.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zl on 2019/1/24 20:11
 */
public interface QuestionService
{
    public List<Question> getLatestQuestions(int userId,int offset,int limit);
    public Question getById(int id) ;
    public int addQuestion(Question question);
    public int updateCommentCount(int id, int count);
}
