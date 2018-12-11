package com.itonglian.dao;

public interface UserDao {

    public void syncUser();

    public void clear();

    public String findAppPushCodeByUserId(String userId);


    public void updateUser();

    void registerAppPushCode(String userId,String appPushCode);

}
