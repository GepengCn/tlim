package com.itonglian.entity;
/**
 * <p> 概述：会话实体
 * <p> 功能：会话实体
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class OfSession {
    /** 会话id;唯一;主键;uuid */
    private String session_id;
    /** 会话类型 */
    private int session_type;
    /** 会话名 */
    private String session_name;
    /** 会话创建时间 */
    private String session_create_time;
    /** 会话修改时间 */
    private String session_modify_time;
    /** 会话删除时间 */
    private String session_delete_time;
    /** 会话是否有效[0/1:有效/无效] */
    private int session_valid;
    /** 会话创建者 */
    private String session_user;
    /** 会话图标 */
    private String session_pic;


    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getSession_type() {
        return session_type;
    }

    public void setSession_type(int session_type) {
        this.session_type = session_type;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getSession_create_time() {
        return session_create_time;
    }

    public void setSession_create_time(String session_create_time) {
        this.session_create_time = session_create_time;
    }

    public String getSession_modify_time() {
        return session_modify_time;
    }

    public void setSession_modify_time(String session_modify_time) {
        this.session_modify_time = session_modify_time;
    }

    public String getSession_delete_time() {
        return session_delete_time;
    }

    public void setSession_delete_time(String session_delete_time) {
        this.session_delete_time = session_delete_time;
    }

    public int getSession_valid() {
        return session_valid;
    }

    public void setSession_valid(int session_valid) {
        this.session_valid = session_valid;
    }

    public String getSession_user() {
        return session_user;
    }

    public void setSession_user(String session_user) {
        this.session_user = session_user;
    }

    public String getSession_pic() {
        return session_pic;
    }

    public void setSession_pic(String session_pic) {
        this.session_pic = session_pic;
    }
}
