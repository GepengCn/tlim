package com.itonglian.dao.impl;

import com.itonglian.dao.SessionDao;
import com.itonglian.entity.OfSession;
import com.itonglian.enums.DBType;
import com.itonglian.mapper.mysql.SessionMapper;
import com.itonglian.utils.DBUtils;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SessionDaoImpl implements SessionDao {

    private static final SessionDao sessionDao = new SessionDaoImpl();

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static SessionDao getInstance(){
        return sessionDao;
    }

    @Override
    public boolean add(OfSession ofSession) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();

        try {
            if(DBUtils.getDBType()== DBType.SQLServer){
                com.itonglian.mapper.sqlserver.SessionMapper sessionMapper = session.getMapper(com.itonglian.mapper.sqlserver.SessionMapper.class);
                sessionMapper.insertSession(ofSession);
            }else{
                SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
                sessionMapper.insertSession(ofSession);
            }

            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
            return false;
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public void delete(String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            sessionMapper.deleteById(sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void update(OfSession session) {

    }

    @Override
    public void updateNameById(String sessionId, String sessionName,String modifyTime) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            sessionMapper.rename(sessionName,modifyTime,sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public OfSession findEntityById(String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            List<OfSession> ofSessions = sessionMapper.findBySessionId(sessionId);
            if(ofSessions!=null&&ofSessions.size()>0){
                return ofSessions.get(0);
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<OfSession> findSessionsByUser(String userId,int valid) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        List<OfSession> ofSessionList = new ArrayList<>();
        try {
            ofSessionList = sessionMapper.findByUser(userId,valid,99);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofSessionList;
    }


    @Override
    public void updatePic(String sessionId, String sessionPic) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            sessionMapper.updatePic(sessionPic,sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void modify(String sessionId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            sessionMapper.modify(MessageUtils.getTs(),sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void switchSession(String sessionId, int valid) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        SessionMapper sessionMapper = session.getMapper(SessionMapper.class);
        try {
            sessionMapper.switchSession(valid,sessionId);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }


}
