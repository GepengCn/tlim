package com.itonglian.dao.impl;

import com.itonglian.dao.ChatDao;
import com.itonglian.entity.OfChat;
import com.itonglian.mapper.mysql.ChatMapper;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.MyBatisSessionFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ChatDaoImpl implements ChatDao {

    private static final ChatDao chatDao = new ChatDaoImpl();

    private static final String CLEAR_CHAT_HISTORY = "DELETE FROM ofmessage  WHERE msg_from = ? AND msg_to = ?";

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static ChatDao getInstance(){
        return chatDao;
    }


    @Override
    public List<OfChat> chatList(String userId) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        List<OfChat> ofChats = new ArrayList<>();
        ChatMapper chatMapper = session.getMapper(ChatMapper.class);
        try {
            ofChats = chatMapper.chatList(userId);
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return ofChats;
    }

    @Override
    public boolean isExistChat(String chat_user,String chat_other) {
        boolean isExistChat = false;
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        ChatMapper chatMapper = session.getMapper(ChatMapper.class);
        try {
            int count = chatMapper.isExistChat(chat_user,chat_other);
            if(count>0){
                isExistChat = true;
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
        return isExistChat;
    }

    @Override
    public void add(OfChat ofChat) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        ChatMapper chatMapper = session.getMapper(ChatMapper.class);
        try {
            chatMapper.insertChat(ofChat);
            session.commit();
        } catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
            session.rollback();
        }finally {
            session.close();
        }

    }

    @Override
    public void modify(String chat_user,String chat_other) {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        ChatMapper chatMapper = session.getMapper(ChatMapper.class);
        try {
            chatMapper.modify(MessageUtils.getTs(),chat_user,chat_other);
            session.commit();
        } catch (Exception e){
            session.rollback();
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            session.close();
        }
    }


    @Override
    public void clearChatHistory(String user_id, String other_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CLEAR_CHAT_HISTORY);
            int i=1;
            preparedStatement.setString(i++,user_id);
            preparedStatement.setString(i++,other_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void save(OfChat ofChat) {
        String chat_user = ofChat.getChat_user();
        String chat_other = ofChat.getChat_other();
        if(isExistChat(chat_user,chat_other)){
            modify(chat_user,chat_other);
        }else{
            add(ofChat);
        }
    }

}
