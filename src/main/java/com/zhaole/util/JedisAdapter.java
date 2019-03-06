package com.zhaole.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhaole.controller.CommentController;
import com.zhaole.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;


/**
 */
@Service
public class JedisAdapter implements InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    public static void print(int index, Object obj)
    {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    /*public static void main(String[] argv)
    {
        Jedis jedis = new Jedis("redis://192.168.7.125:6379/9");
        jedis.flushDB();
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

    }*/

    public Jedis getJedis()
    {
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis)
    {
        try{
            return jedis.multi();  //开启事务
        }catch (Exception e){
            logger.error("事务开启异常" + e.getMessage());
        }
        return null;
    }

    //返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
    public List<String> lrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.lrange(key, start, end);
        }catch (Exception e){
            logger.error("取值发生异常" + e.getMessage());
        }finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

    //multi开启事务执行之后再zsort中返回的是list
    //(事务块内所有命令的返回值，按命令执行的先后顺序排列。 当操作被打断时，返回空值 nil )
    public List<Object> exec(Transaction tx, Jedis jedis)
    {
        try{
            return tx.exec();   //执行事务
        }catch (Exception e){
            logger.error("事务启动异常" +  e.getMessage());
            tx.discard();
        }finally {
            //当事务不为空的时候要将其关闭
            if (tx != null)
            {
                try{
                    tx.close();
                }catch (Exception e){
                    logger.error("事务关闭异常" + e.getMessage());
                }
            }
            //把jedis关闭
            if(jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        pool = new JedisPool("redis://192.168.7.125:6379/10");
    }

    //Redis Zrevrange 命令返回有序集中，指定区间内的成员。
    //其中成员的位置按分数值递减(从大到小)来排列。
    public Set<String> zrevrange(String key, int start, int end)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

    //把用户加到关注的列表中
    public long zadd(String key, double score, String value)
    {
        Jedis jedis  = null;
        try{
            jedis  = pool.getResource();
            return jedis.zadd(key,score,value);
        }catch (Exception e){
            logger.error("添加用户关注失败" + e.getMessage());
        }finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }
    public long zcard(String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key);
        }catch (Exception e){
            logger.error("发生异常" +  e.getMessage());
        }finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return 0;
    }

    public Double zscore(String key, String member)
    {       //Double是类，是double的实现类，double是属性
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        }catch (Exception e){
            logger.error("发生异常" + e.getMessage());
        }finally {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }
    public long sadd(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key)
    {
        //Scard 命令返回集合中元素的数量
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
    //按照给出的 key 顺序查看 list，并在找到的第一个非空 list 的尾部弹出一个元素。
    public List<String> brpop(int timeout, String key)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value)
    {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
