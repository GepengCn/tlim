package com.itonglian.dao;

import com.itonglian.entity.OfSubscriber;

import java.util.List;

/**
 * <p> 概述：会话Dao层
 * <p> 功能：会话Dao层
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public interface SubscriberDao {

    public boolean add(OfSubscriber subscriber);

    public void delete(String userId,String sessionId);

    public void update(OfSubscriber subscriber);

    public OfSubscriber findEntityById(String userId);

    public List<OfSubscriber> findSubscribers(String sessionId);

    public void deleteBySession(String sessionId);
}
