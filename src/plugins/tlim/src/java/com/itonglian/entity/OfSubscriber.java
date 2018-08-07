package com.itonglian.entity;
/**
 * <p> 概述：会话订阅者实体
 * <p> 功能：会话订阅者实体
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class OfSubscriber {
    /**  用户id;主键;uuid*/
    private String userId;
    /**  用户姓名*/
    private String userName;
    /**  登录名*/
    private String acctLogin;
    /**  头像url*/
    private String pic;
    /**  会话id*/
    private String sessionId;
    /**  时间戳*/
    private String ts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcctLogin() {
        return acctLogin;
    }

    public void setAcctLogin(String acctLogin) {
        this.acctLogin = acctLogin;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
