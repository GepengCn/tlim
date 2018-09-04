package com.itonglian.dao.impl;

import com.itonglian.dao.ChatDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfStatus;
import com.itonglian.utils.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.database.SequenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatDaoImpl implements ChatDao {

    private static final ChatDao chatDao = new ChatDaoImpl();

    private static final StatusDao statusDao = StatusDaoImpl.getInstance();

    private static final String INSERT = "INSERT INTO ofmessage (msg_id,msg_type,msg_from,msg_to,msg_time,body) VALUES(?,?,?,?,?,?)";

    private static final String DELETE = "DELETE FROM ofmessage WHERE msg_id=?";

    private static final String QUERY_BY_ID = "SELECT * FROM ofmessage WHERE msg_id = ?";

    private static final String INSERT_WITH_SESS = "INSERT INTO ofmessage (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(?,?,?,?,?,?,?,?)";

    private static final String IS_EXIST = "SELECT count(*) AS total FROM ofmessage WHERE msg_id=? AND msg_to=?";

    private static final String TIK_TALK = "SELECT * FROM ofmessage WHERE id_ IN (SELECT max(id_) from (SELECT id_, msg_id, msg_type, msg_to AS send, msg_from AS recv, msg_time, body FROM ofmessage WHERE msg_from = ? AND msg_type like ? UNION SELECT id_, msg_id, msg_type, msg_from AS send, msg_to   AS recv, msg_time, body FROM ofmessage WHERE msg_to = ? AND msg_type like ?) t GROUP BY send)";



    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static ChatDao getInstance(){
        return chatDao;
    }

    @Override
    public void add(OfMessage ofMessage) {
        if(this.isExist(ofMessage.getMsg_id(),ofMessage.getMsg_to())>0){
            return;
        }
        String msg_type = ofMessage.getMsg_type();
        String isCommand = msg_type.split("-")[1].substring(0,1);
        if(!ofMessage.getMsg_from().equals(ofMessage.getMsg_to())&&"0".equals(isCommand)){
            OfStatus ofStatus = new OfStatus();
            ofStatus.setMsg_id(ofMessage.getMsg_id());
            ofStatus.setMsg_to(ofMessage.getMsg_to());
            ofStatus.setMsg_type(ofMessage.getMsg_type());
            ofStatus.setSession_id(ofMessage.getSession_id());
            ofStatus.setStatus(0);
            statusDao.add(ofStatus);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_WITH_SESS);
            int i=1;
            preparedStatement.setLong(i++,SequenceManager.nextID(OfMessage.ID_Contants.MSG_KEY));
            preparedStatement.setString(i++,ofMessage.getMsg_id());
            preparedStatement.setString(i++,ofMessage.getMsg_type());
            preparedStatement.setString(i++,ofMessage.getMsg_from());
            preparedStatement.setString(i++,ofMessage.getMsg_to());
            preparedStatement.setString(i++,ofMessage.getMsg_time());
            preparedStatement.setString(i++,MessageUtils.encode(ofMessage.getBody()));
            preparedStatement.setString(i++,ofMessage.getSession_id());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }


    }

    @Override
    public void delete(String msgId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            int i=1;
            preparedStatement.setString(i++,msgId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void update(OfMessage ofMessage) {

    }

    @Override
    public OfMessage findEntityById(String msgId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_BY_ID);
            preparedStatement.setString(1,msgId);
            resultSet = preparedStatement.executeQuery();
            OfMessage ofMessage = new OfMessage();
            if(resultSet.next()){
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_type(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                return ofMessage;
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public List<OfMessage> findList(Map<String, Object> conditions) {
        return null;
    }

    @Override
    public int isExist(String msgId, String msgTo) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(IS_EXIST);
            int i=1;
            preparedStatement.setString(i++,msgId);
            preparedStatement.setString(i++,msgTo);
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
    public List<OfMessage> tikTalk(String userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfMessage> messageList = new ArrayList<OfMessage>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(TIK_TALK);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,"%MTT%");
            preparedStatement.setString(3,userId);
            preparedStatement.setString(4,"%MTT%");

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
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return messageList;
    }

}
