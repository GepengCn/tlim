package com.itonglian.dao.impl;

import com.itonglian.dao.MessageDao;
import com.itonglian.entity.Message;
import com.itonglian.entity.OfMessage;
import com.itonglian.enums.DBType;
import com.itonglian.mapper.mysql.MessageMapper;
import com.itonglian.utils.DBUtils;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<OfMessage> messageList = new ArrayList<>();
        try {
            if(DBUtils.getDBType()== DBType.Oracle){
                com.itonglian.mapper.oracle.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.oracle.MessageMapper.class);
                messageList = messageMapper.findPageBySession(session_id,start,length);
            }else if(DBUtils.getDBType()== DBType.SQLServer){
                com.itonglian.mapper.sqlserver.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.sqlserver.MessageMapper.class);
                messageList = messageMapper.findPageBySession(session_id,start,start+length);
            }else{
                MessageMapper messageMapper = session.getMapper(MessageMapper.class);
                messageList = messageMapper.findPageBySession(session_id,start,length);
            }
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
        ofMessage.setId_(UUID.randomUUID().toString());
        try {
            if(DBUtils.getDBType()== DBType.Oracle){
                com.itonglian.mapper.oracle.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.oracle.MessageMapper.class);
                int isExist = messageMapper.isExist(ofMessage.getMsg_id());
                if(isExist>0||isExist==-1){
                    return;
                }
                messageMapper.insertMessage(ofMessage);
            }else if(DBUtils.getDBType()== DBType.SQLServer){
                com.itonglian.mapper.sqlserver.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.sqlserver.MessageMapper.class);
                int isExist = messageMapper.isExist(ofMessage.getMsg_id());
                if(isExist>0||isExist==-1){
                    return;
                }
                messageMapper.insertMessage(ofMessage);
            }else{
                MessageMapper messageMapper = session.getMapper(MessageMapper.class);
                int isExist = messageMapper.isExist(ofMessage.getMsg_id());
                if(isExist>0||isExist==-1){
                    return;
                }
                messageMapper.insertMessage(ofMessage);
            }
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

        List<OfMessage> messageList = new ArrayList<>();
        try {
            if(DBUtils.getDBType()== DBType.Oracle){
                com.itonglian.mapper.oracle.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.oracle.MessageMapper.class);
                messageList = messageMapper.findPageByChat(msg_from,msg_to,"%MTT%",start,length);
            }else {
                MessageMapper messageMapper = session.getMapper(MessageMapper.class);
                messageList = messageMapper.findPageByChat(msg_from,msg_to,"%MTT%",start,length);
            }

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
            total = messageMapper.findPageTotalByChat(msg_from,msg_to,"%MTT%");
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
    public boolean deleteById(String msg_id) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        try {
            messageMapper.deleteById(msg_id);
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
            return false;
        }finally {
            session.close();
        }
        return true;
    }

    @Override
    public List<OfMessage> findSystemHistory(String msg_to, int start, int length) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        List<OfMessage> messageList = new ArrayList<>();
        try {
            if(DBUtils.getDBType()== DBType.Oracle){
                com.itonglian.mapper.oracle.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.oracle.MessageMapper.class);
                messageList = messageMapper.findPageBySystem("MTB-100",msg_to,start,length);
            }else if(DBUtils.getDBType()== DBType.SQLServer){
                com.itonglian.mapper.sqlserver.MessageMapper messageMapper = session.getMapper(com.itonglian.mapper.sqlserver.MessageMapper.class);
                messageList = messageMapper.findPageBySystem("MTB-100",msg_to,start,start+length);
            }else{
                MessageMapper messageMapper = session.getMapper(MessageMapper.class);
                messageList = messageMapper.findPageBySystem("MTB-100",msg_to,start,length);
            }
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageList;
    }

    @Override
    public int findSystemMessageTotal(String msg_to) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);
        int total = 0;
        try {
            total = messageMapper.findPageTotalBySystem("MTB-100",msg_to);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return total;
    }

    @Override
    public List<Message> findByTime(long msg_time) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        MessageMapper messageMapper = session.getMapper(MessageMapper.class);

        List<Message> messageList = new ArrayList<>();
        try {
            messageList = messageMapper.findByTime(msg_time);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return messageList;
    }
}
