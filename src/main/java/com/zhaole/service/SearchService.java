package com.zhaole.service;

import com.zhaole.model.Question;

import java.util.List;

/**
 * created by zl on 2019/2/17 16:06
 */
public interface SearchService
{
    public List<Question> searchQuestion(String keyword, int offset, int count, String hlPer, String hlPos) throws Exception;
    public boolean indexQuestion(int qid, String title, String content) throws Exception;
}
