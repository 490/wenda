package com.zhaole.dao;

import com.zhaole.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * created by zl on 2019/1/25 9:33
 */
@Repository
@Mapper
public interface LoginTicketDAO {
    String tableName = "login_ticket";
    String insertFields = " user_id, expired, status, ticket ";
    String selectFields = " id, " +insertFields;

    @Insert({"insert into ",tableName,"(",insertFields,") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ",selectFields," from ",tableName," where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ",tableName," set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket,
                      @Param("status") int status);

}
