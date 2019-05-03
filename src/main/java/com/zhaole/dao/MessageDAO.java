package com.zhaole.dao;


import com.zhaole.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageDAO
{
    String tableName = " message ";
    String insertFields = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String selectFileds = " id, "+insertFields;

    @Insert({"insert into ",tableName," (",insertFields,") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);


    /* select from_id, to_id,content,has_read,conversation_id,created_date,count(id) as id from
        (select * from message where from_id=#{userid} or toid=#{userid} order by created_date desc)
        tt group by conversation_id order by created_date desc limit #{offset},#{limit};
    * */
    @Select({"select ", insertFields, " , count(id) as id from " +
            "( " +
            "select * from ", tableName,
            " where from_id=#{userId} or to_id=#{userId} order by created_date desc) " +

            "tt group by conversation_id order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(id) from ", tableName, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
    @Select({"select ", selectFileds, " from ", tableName,
            " where conversation_id=#{conversationId} order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
}
