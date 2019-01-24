package com.zhaole.dao;

import com.zhaole.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by zl on 2019/1/24 21:29
 */
@Mapper
@Repository
public interface QuestionDAO
{
    String tableName = " question ";
    String insertFields = " title, content, created_date, user_id, comment_count ";
    String selectFields = " id, " + insertFields;

    @Insert({"insert into ", tableName, "(", insertFields,
            ") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId, @Param("offset") int offset,
                                         @Param("limit") int limit);
}
