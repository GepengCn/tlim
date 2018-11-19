package com.itonglian.dao.impl;

import com.itonglian.dao.StatusDao;
import com.itonglian.entity.OfStatus;
import com.itonglian.mapper.StatusMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StatusDaoImpl implements StatusDao {

    private static final StatusDao statusDao = new StatusDaoImpl();

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static StatusDao getInstance(){
        return statusDao;
    }

    @Override
    public void add(OfStatus ofStatus) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            statusMapper.insertStatus(ofStatus);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void update(String msg_id,int msg_status) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            statusMapper.update(msg_status,msg_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public List<OfStatus> findByReader(String reader) {
        List<OfStatus> ofStatuses = new ArrayList<>();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            ofStatuses = statusMapper.findByReader(reader);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofStatuses;
    }

    @Override
    public void delete(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            statusMapper.delete(msg_id);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }


}
