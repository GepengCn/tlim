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

    public void add(OfSession session);

    public void delete(String sessionId);

    public void update(OfSession session);

    public void updateNameById(String sessionId,String sessionName);

    public OfSession findEntityById(String sessionId);

    public List<OfSession> findSessionsByUser(String userId);

    public void updatePic(String sessionId,String sessionPic);

    public void modify(String sessionId);


}
