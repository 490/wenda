package com.zhaole.model;

import java.util.HashMap;
import java.util.Map;

/**
 * created by zl on 2019/1/24 20:06
 */
public class ViewObject
{
    private Map<String,Object> objectMap = new HashMap<String, Object>();
    public void set(String key,Object value)
    {
        objectMap.put(key,value);
    }
    public Object get(String key)
    {
        return objectMap.get(key);
    }
}
