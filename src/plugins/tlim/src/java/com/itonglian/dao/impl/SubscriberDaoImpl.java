package com.itonglian.dao.impl;

import com.itonglian.dao.SubscriberDao;
import com.itonglian.entity.OfSubscriber;
import com.itonglian.mapper.mysql.SubscriberMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SubscriberDaoImpl implements SubscriberDao {

    private static final SubscriberDao subscriberDao = new SubscriberDaoImpl();

    private static final Logger Log = LoggerFactory.getLogger(SubscriberDaoImpl.class);

    public static SubscriberDao getInstance(){
        return subscriberDao;
    }

    @Override
    public void add(OfSubscriber subscriber) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SubscriberMapper subscriberMapper = session.getMapper(SubscriberMapper.class);
        try {
            subscriberMapper.insertSubscriber(subscriber);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(String userId,String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SubscriberMapper subscriberMapper = session.getMapper(SubscriberMapper.class);
        try {
            subscriberMapper.deleteBySessionAndUser(userId,sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void update(OfSubscriber subscriber) {

    }

    @Override
    public OfSubscriber findEntityById(String userId) {
        return null;
    }

    @Override
    public List<OfSubscriber> findSubscribers(String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        List<OfSubscriber> ofSubscriberList = new ArrayList<>();
        SubscriberMapper subscriberMapper = session.getMapper(SubscriberMapper.class);
        try {
            ofSubscriberList = subscriberMapper.findBySession(sessionId);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofSubscriberList;
    }

    @Override
    public void deleteBySession(String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SubscriberMapper subscriberMapper = session.getMapper(SubscriberMapper.class);
        try {
            subscriberMapper.deleteBySession(sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }


}
