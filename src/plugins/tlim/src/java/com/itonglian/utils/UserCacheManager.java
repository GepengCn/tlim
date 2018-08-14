package com.itonglian.utils;

import com.itonglian.entity.User;

import java.util.concurrent.ConcurrentHashMap;

public class UserCacheManager {

    private static ConcurrentHashMap<String,User> users = new ConcurrentHashMap<String,User>();


    public static ConcurrentHashMap<String,User> findAll(){
        return users;
    }

    public static void clear(){
        users.clear();
    }

    public static void add(User user){
        String key = user.getUser_id();
        remove(key);
        users.put(key,user);
    }

    public static void remove(String key){
        if(users.containsKey(key)){
            users.remove(key);
        }
    }

    public static User findUserByKey(String key){
        return users.get(key);
    }
}
