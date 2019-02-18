package com.zhaole.dao;

import com.zhaole.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by zl on 2019/2/16 23:02
 */
@Mapper
@Repository
public interface FeedDAO
{
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = " user_id, data, created_date, type ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,") values (#{userId},#{date},#{createDate},#{type})"})
    int addFeed(Feed feed);

    //推
    @Select({"select ",SELECT_FIELDS, " from ",TABLE_NAME," where id=#{id}"})
    Feed getFeedById(int id);


    /**
     * 选择从哪个用户那拉新鲜事
     * @param maxId 最多看这些
     * @param userIds 来源：我关注的用户。登陆状态才用，否则只用另两个。动态sql来判断
     * @param count 分页
     * @return
     */
    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);
}
