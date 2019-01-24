package com.zhaole.model;

/**
 * created by zl on 2019/1/24 15:51
 */
public class User {
    public User(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription(){
        return "This is "+ name;
    }
}
