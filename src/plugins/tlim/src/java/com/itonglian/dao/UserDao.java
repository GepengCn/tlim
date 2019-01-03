package com.itonglian.dao;

import com.itonglian.entity.User;

import java.util.List;

public interface UserDao {

    void syncUser();

    void clear();

    String findAppPushCodeByUserId(String userId);


    void updateUser();

    void registerAppPushCode(String userId,String appPushCode);

    int count();

    List<User> findAll();

    void remove(String userId);

}
