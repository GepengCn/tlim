package com.itonglian.dao.impl;

import com.itonglian.dao.MessageDao;
import com.itonglian.entity.OfMessage;
import com.itonglian.utils.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private static final Logger Log = LoggerFactory.getLogger(MessageDaoImpl.class);

    private static final MessageDao messageDao = new MessageDaoImpl();

    private static final String FIND_HISTORY = "SELECT * FROM ofmessage WHERE session_id = ? AND msg_to = ? AND msg_type !='MTS-100' ORDER BY msg_time desc limit ?,?";

    private static final String FIND_MESSAGE_TOTAL = "SELECT COUNT(*) AS total FROM ofmessage WHERE session_id = ? AND msg_to = ? AND msg_type !='MTS-100'";

    private static final String FIND_CHAT_HISTORY = "SELECT * FROM ofmessage WHERE msg_from=? AND msg_to = ? AND msg_type LIKE ? AND msg_type !='MTT-100' UNION SELECT * FROM ofmessage WHERE msg_from = ? AND msg_to = ? AND msg_type LIKE ? AND msg_type !='MTT-100' ORDER BY msg_time desc limit ?,?";

    private static final String FIND_CHAT_TOTAL = "SELECT COUNT(*) AS total FROM (SELECT * FROM ofmessage WHERE msg_from=? AND msg_to = ? AND msg_type LIKE ? AND msg_type !='MTT-100' UNION SELECT * FROM ofmessage WHERE msg_from = ? AND msg_to = ?  AND msg_type LIKE ? AND msg_type !='MTT-100' ) t ";

    private static final String DELETE_BY_USER = "DELETE FROM ofmessage WHERE session_id = ? AND msg_from = ? ";

    private static final String DELETE_BY_SESSION = "DELETE FROM ofmessage WHERE session_id = ?";

    public static MessageDao getInstance(){
        return messageDao;
    }

    @Override
    public List<OfMessage> findHistory(String session_id,String user_id, int start, int length) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfMessage> messageList = new ArrayList<OfMessage>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_HISTORY);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setString(i++,user_id);
            preparedStatement.setInt(i++,start);
            preparedStatement.setInt(i++,length);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfMessage ofMessage = new OfMessage();
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_to(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                ofMessage.setSession_id(resultSet.getString("session_id"));
                messageList.add(ofMessage);
            }
            return messageList;
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public int findMessageTotal(String session_id, String user_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_MESSAGE_TOTAL);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setString(i++,user_id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("total");
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return 0;
    }

    @Override
    public List<OfMessage> findChatHistory(String msg_from, String msg_to, int start, int length) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfMessage> messageList = new ArrayList<OfMessage>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CHAT_HISTORY);
            int i=1;
            preparedStatement.setString(i++,msg_from);
            preparedStatement.setString(i++,msg_to);
            preparedStatement.setString(i++,"%MTT%");
            preparedStatement.setString(i++,msg_to);
            preparedStatement.setString(i++,msg_from);
            preparedStatement.setString(i++,"%MTT%");
            preparedStatement.setInt(i++,start);
            preparedStatement.setInt(i++,length);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfMessage ofMessage = new OfMessage();
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_to(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                ofMessage.setSession_id(resultSet.getString("session_id"));
                messageList.add(ofMessage);
            }
            return messageList;
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public int findChatMessageTotal(String msg_from, String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CHAT_TOTAL);
            int i=1;
            preparedStatement.setString(i++,msg_from);
            preparedStatement.setString(i++,msg_to);
            preparedStatement.setString(i++,"%MTT%");
            preparedStatement.setString(i++,msg_to);
            preparedStatement.setString(i++,msg_from);
            preparedStatement.setString(i++,"%MTT%");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("total");
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return 0;
    }

    @Override
    public void deleteByUser(String session_id, String msg_from) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BY_USER);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setString(i++,msg_from);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void deleteBySession(String session_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BY_SESSION);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }
}
