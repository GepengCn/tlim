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
    private String user_id;
    /**  用户姓名*/
    private String user_name;
    /**  登录名*/
    private String acct_login;
    /**  头像url*/
    private String pic;
    /**  会话id*/
    private String session_id;
    /**  时间戳*/
    private String ts;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAcct_login() {
        return acct_login;
    }

    public void setAcct_login(String acct_login) {
        this.acct_login = acct_login;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
