package com.zhaole.service.impl;

import com.zhaole.model.Question;
import com.zhaole.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zl on 2019/1/24 20:13
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    public List<Question> getLatestQuestions(int userId,int offset,int limit)
    {
        return
    }
}
