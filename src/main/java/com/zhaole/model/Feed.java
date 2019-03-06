package com.zhaole.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * created by zl on 2019/2/16 22:46
 */
public class Feed
{
    private int id;
    private int type;//新鲜事类型,如这条新鲜事是a关注b，是x评论y
    private int userId;//
    private Date createdDate;
    private String data;
    private JSONObject jsonObject = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.jsonObject = JSONObject.parseObject(data);
    }

    public String getJsonObject(String key)
    {
        return jsonObject== null ? null : jsonObject.getString(key);
    }



}
