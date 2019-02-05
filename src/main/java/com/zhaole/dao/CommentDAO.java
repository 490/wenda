package com.zhaole.dao;

import com.zhaole.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentDAO {
    String tableName = " comment ";
    String insertField = " user_id, content, created_date, entity_id, entity_type, status ";
    String selectField = " id, " + insertField;

    @Insert({"insert into ", tableName, "(", insertField,
            ") values (#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", selectField, " from ", tableName, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select ", selectField, " from ", tableName,
            " where entity_id=#{entityId} and entity_type=#{entityType} order by created_date desc"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", tableName, " where entity_id=#{entityId} and entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
