package com.itonglian.dao.impl;

import com.itonglian.dao.OfflineDao;
import com.itonglian.entity.OfCustomOffline;
import com.itonglian.mapper.OfflineMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OfflineDaoImpl implements OfflineDao {

    private static final OfflineDao offlineDao = new OfflineDaoImpl();

    public static OfflineDao getInstance(){
        return offlineDao;
    }

    private static final Logger Log = LoggerFactory.getLogger(OfflineDaoImpl.class);


    @Override
    public void add(OfCustomOffline ofCustomOffline) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            offlineMapper.insertOffline(ofCustomOffline);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }


    @Override
    public void updateStatus(String msg_id, int msg_status) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            offlineMapper.updateStatus(msg_status,msg_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            offlineMapper.deleteByMsgId(msg_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void deleteByUser(String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            offlineMapper.deleteByUser(user_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public List<OfCustomOffline> findByUser(String user_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        List<OfCustomOffline> ofCustomOfflines = new ArrayList<>();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            ofCustomOfflines = offlineMapper.findByUser(user_id);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofCustomOfflines;
    }

    @Override
    public OfCustomOffline findByMsgId(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            List<OfCustomOffline> ofCustomOfflines = offlineMapper.findByMsgId(msg_id);
            if(ofCustomOfflines!=null&&ofCustomOfflines.size()>0){
                return ofCustomOfflines.get(0);
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<OfCustomOffline> findAll() {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        List<OfCustomOffline> ofCustomOfflines = new ArrayList<>();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            ofCustomOfflines = offlineMapper.findAll();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofCustomOfflines;
    }

    @Override
    public void deleteBySession(String session_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        OfflineMapper offlineMapper = session.getMapper(OfflineMapper.class);
        try {
            offlineMapper.deleteBySession(session_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }
}
