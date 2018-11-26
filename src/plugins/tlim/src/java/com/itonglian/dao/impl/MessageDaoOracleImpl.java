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

public class MessageDaoOracleImpl extends MessageDaoImpl implements MessageDao {

    private static final Logger Log = LoggerFactory.getLogger(MessageDaoOracleImpl.class);

    private static final String FIND_CHAT_HISTORY = "SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE msg_from=? AND msg_to = ? AND msg_type LIKE ? AND msg_type !='MTT-100' UNION SELECT * FROM ofmessage WHERE msg_from = ? AND msg_to = ? AND msg_type LIKE ? AND msg_type !='MTT-100' AND rownum<=? ORDER BY msg_time desc limit ?,?) WHERE rn>=?";

    private static final MessageDao messageDao = new MessageDaoOracleImpl();

    private static final String FIND_HISTORY = "SELECT * FROM (SELECT ofmessage.*,rownum as rn FROM ofmessage WHERE session_id = ?  AND msg_type !='MTS-100' AND rownum<=? ORDER BY msg_time desc) WHERE rn>=?";

    public static MessageDao getInstance(){
        return messageDao;
    }

    @Override
    public List<OfMessage> findHistory(String session_id,int start, int length) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfMessage> messageList = new ArrayList<OfMessage>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_HISTORY);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setInt(i++,start+length);
            preparedStatement.setInt(i++,start);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfMessage ofMessage = new OfMessage();
                ofMessage.setId_(resultSet.getString("id_"));
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
            preparedStatement.setInt(i++,start+length);
            preparedStatement.setInt(i++,start);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OfMessage ofMessage = new OfMessage();
                ofMessage.setId_(resultSet.getString("id_"));
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
}
