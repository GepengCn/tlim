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
    private String sessionId;
    /** 会话类型 */
    private int sessionType;
    /** 会话名 */
    private String sessionName;
    /** 会话创建时间 */
    private String sessionCreateTime;
    /** 会话修改时间 */
    private String sessionModifyTime;
    /** 会话删除时间 */
    private String sessionDeleteTime;
    /** 会话是否有效[0/1:有效/无效] */
    private int sessionValid;
    /** 会话创建者 */
    private String sessionUser;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionCreateTime() {
        return sessionCreateTime;
    }

    public void setSessionCreateTime(String sessionCreateTime) {
        this.sessionCreateTime = sessionCreateTime;
    }

    public String getSessionModifyTime() {
        return sessionModifyTime;
    }

    public void setSessionModifyTime(String sessionModifyTime) {
        this.sessionModifyTime = sessionModifyTime;
    }

    public String getSessionDeleteTime() {
        return sessionDeleteTime;
    }

    public void setSessionDeleteTime(String sessionDeleteTime) {
        this.sessionDeleteTime = sessionDeleteTime;
    }

    public int getSessionValid() {
        return sessionValid;
    }

    public void setSessionValid(int sessionValid) {
        this.sessionValid = sessionValid;
    }

    public String getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(String sessionUser) {
        this.sessionUser = sessionUser;
    }


}
