package com.itonglian.dao;

import com.itonglian.entity.OfSession;

import java.util.List;

/**
 * <p> 概述：会话Dao层
 * <p> 功能：会话Dao层
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public interface SessionDao {

    void add(OfSession session);

    void delete(String sessionId);

    void update(OfSession session);

    void updateNameById(String sessionId,String sessionName,String modifyTime);

    OfSession findEntityById(String sessionId);

    List<OfSession> findSessionsByUser(String userId,int valid);

    void updatePic(String sessionId,String sessionPic);

    void modify(String sessionId);

    void switchSession(String sessionId,int valid);


}
