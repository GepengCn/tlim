package com.itonglian.dao;

public interface UserDao {

    public void syncUser();

    public void clear();

    public String findAppPushCodeByUserId(String userId);


}
