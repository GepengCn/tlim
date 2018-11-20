package com.itonglian.dao.impl;

import com.itonglian.dao.MessageDao;
import com.itonglian.entity.Message;
import com.itonglian.entity.OfMessage;
import com.itonglian.mapper.MessageMapper;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private static final Logger Log = LoggerFactory.getLogger(MessageDaoImpl.class);

    private static final MessageDao messageDao = new MessageDaoImpl();

    public static MessageDao getInstance(){
        return messageDao;
    }

    @Override
    public List<OfMessage> findHistory(String session_id,int start, int length) {

        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);

        List<OfMessage> messageList = new ArrayList<>();
        try {
            messageList = messageMapper.findPageBySession(session_id,start,length);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageList;
    }

    @Override
    public void insert(OfMessage ofMessage) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        int isExist = messageMapper.isExist(ofMessage.getMsg_id());
        if(isExist>0||isExist==-1){
            return;
        }
        try {
            messageMapper.insertMessage(ofMessage);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public int findMessageTotal(String session_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        int total = 0;
        try {
            total = messageMapper.findPageTotalBySession(session_id);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return total;
    }

    @Override
    public List<OfMessage> findChatHistory(String msg_from, String msg_to, int start, int length) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);

        List<OfMessage> messageList = new ArrayList<>();
        try {
            messageList = messageMapper.findPageByChat(msg_from,msg_to,"%MTT%",start,length);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageList;
    }

    @Override
    public int findChatMessageTotal(String msg_from, String msg_to) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        int total = 0;
        try {
            total = messageMapper.findPageTotalByChat(msg_from,msg_to,"MTT");
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return total;
    }

    @Override
    public void deleteByUser(String session_id, String msg_from) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        try {
            messageMapper.deleteByUserAndSession(session_id,msg_from);
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
    }

    @Override
    public void deleteBySession(String session_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        try {
            messageMapper.deleteBySession(session_id);
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
    }

    @Override
    public String findMessageTime(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        String msg_time = "";
        try {
            msg_time = messageMapper.findMessageTime(msg_id);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return msg_time;
    }

    @Override
    public List<Message> findMessageAfter(String msg_to, String msg_time) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);

        List<Message> messageList = new ArrayList<>();
        try {
            messageList = messageMapper.findMessageAfter(msg_to,msg_time);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageList;
    }

    @Override
    public void deleteById(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        try {
            messageMapper.deleteById(msg_id);
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
    }
}
