package com.zhaole.dao;

import com.zhaole.model.User;
import org.apache.ibatis.annotations.*;

/**
 * created by zl on 2019/1/24 20:15
 */
@Mapper
public class UserDAO
{

    String tableName = " user ";
    String insertFields = " name, password, salt, head_url ";
    String selectFields = " id, " + insertFields;

    @Insert({"insert into user ( name, password, salt, head_url) values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ", selectFields, " from ", tableName, " where id=#{id}"})
    User selectById(int id);

    @Update({"update ", tableName, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", tableName, " where id=#{id}"})
    void deleteById(int id);
}
