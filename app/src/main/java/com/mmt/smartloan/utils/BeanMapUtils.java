package com.mmt.smartloan.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
public class BeanMapUtils {

    /**
     * 将对象属性转化为map结合
     */
    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null){
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }

}

