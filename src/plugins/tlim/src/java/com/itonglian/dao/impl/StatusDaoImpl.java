package com.itonglian.dao.impl;

import com.itonglian.bean.MessageRead;
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
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
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
    public void update(String msg_id,String reader,int msg_status) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            statusMapper.update(msg_status,msg_id,reader);
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

    @Override
    public boolean isExist(String msg_id, String reader) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        boolean isExist = false;
        try {
            int count = statusMapper.isExist(msg_id,reader);
            if(count>0){
                isExist = true;
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return isExist;
    }

    @Override
    public void save(OfStatus ofStatus) {
        String msg_id = ofStatus.getMsg_id();
        String reader = ofStatus.getReader();
        if(isExist(ofStatus.getMsg_id(),ofStatus.getReader())){
            update(msg_id,reader,ofStatus.getStatus());
        }else{
            add(ofStatus);
        }
    }

    @Override
    public List<MessageRead> findSessionRead(String session_id,int start,int length,String sender) {
        List<MessageRead> messageReadList = new ArrayList<>();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        StatusMapper statusMapper = session.getMapper(StatusMapper.class);
        try {
            messageReadList = statusMapper.findSessionRead(session_id,start,length,sender);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageReadList;
    }


}
