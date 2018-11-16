package com.itonglian.dao.impl;

import com.itonglian.dao.OfflineDao;
import com.itonglian.entity.OfCustomOffline;
import com.itonglian.entity.OfMessage;
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

public class OfflineDaoImpl implements OfflineDao {

    private static final OfflineDao offlineDao = new OfflineDaoImpl();

    public static OfflineDao getInstance(){
        return offlineDao;
    }

    private static final String ADD = "INSERT INTO ofcustomoffline (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id,msg_status) VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String UPDATE_STATUS = "UPDATE ofcustomoffline SET msg_status = ? WHERE msg_id = ?";

    private static final String DELETE = "DELETE FROM ofcustomoffline WHERE msg_id = ?";

    private static final String DELETE_BY_USER = "DELETE FROM ofcustomoffline WHERE msg_to = ?";

    private static final String FIND_BY_USER = "SELECT * FROM ofcustomoffline WHERE msg_to = ?";

    private static final String FIND_BY_MSG_ID = "SELECT * FROM ofcustomoffline WHERE msg_id = ?";

    private static final String FIND_ALL = "SELECT * FROM ofcustomoffline";


    private static final Logger Log = LoggerFactory.getLogger(OfflineDaoImpl.class);


    @Override
    public void add(OfMessage ofMessage) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(ADD);
            int i=1;
            preparedStatement.setLong(i++, SequenceManager.nextID(OfCustomOffline.ID_Contants.MSG_KEY));
            preparedStatement.setString(i++,ofMessage.getMsg_id());
            preparedStatement.setString(i++,ofMessage.getMsg_type());
            preparedStatement.setString(i++,ofMessage.getMsg_from());
            preparedStatement.setString(i++,ofMessage.getMsg_to());
            preparedStatement.setString(i++,ofMessage.getMsg_time());
            preparedStatement.setString(i++, MessageUtils.encode(ofMessage.getBody()));
            preparedStatement.setString(i++,ofMessage.getSession_id());
            preparedStatement.setInt(i++,0);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void updateAfterAdd(OfMessage ofMessage, int msg_status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(ADD);
            int i=1;
            preparedStatement.setLong(i++, SequenceManager.nextID(OfCustomOffline.ID_Contants.MSG_KEY));
            preparedStatement.setString(i++,ofMessage.getMsg_id());
            preparedStatement.setString(i++,ofMessage.getMsg_type());
            preparedStatement.setString(i++,ofMessage.getMsg_from());
            preparedStatement.setString(i++,ofMessage.getMsg_to());
            preparedStatement.setString(i++,ofMessage.getMsg_time());
            preparedStatement.setString(i++, MessageUtils.encode(ofMessage.getBody()));
            preparedStatement.setString(i++,ofMessage.getSession_id());
            preparedStatement.setInt(i++,msg_status);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void updateStatus(String msg_id, int msg_status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_STATUS);
            preparedStatement.setInt(1,msg_status);
            preparedStatement.setString(2,msg_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void delete(String msg_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            int i=1;
            preparedStatement.setString(i++,msg_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void deleteByUser(String user_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_BY_USER);
            int i=1;
            preparedStatement.setString(i++,user_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public List<OfCustomOffline> findByUser(String user_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfCustomOffline> messageList = new ArrayList<OfCustomOffline>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_USER);
            int i=1;
            preparedStatement.setString(i++,user_id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfCustomOffline ofMessage = new OfCustomOffline();
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_to(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                ofMessage.setSession_id(resultSet.getString("session_id"));
                ofMessage.setMsg_status(resultSet.getInt("msg_status"));
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
    public OfCustomOffline findByMsgId(String msg_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_MSG_ID);
            preparedStatement.setString(1,msg_id);
            resultSet = preparedStatement.executeQuery();
            OfCustomOffline ofMessage = new OfCustomOffline();
            if(resultSet.next()){
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_type(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                ofMessage.setMsg_status(resultSet.getInt("msg_status"));
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
    public List<OfCustomOffline> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfCustomOffline> messageList = new ArrayList<OfCustomOffline>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfCustomOffline ofMessage = new OfCustomOffline();
                ofMessage.setId_(resultSet.getLong("id_"));
                ofMessage.setMsg_id(resultSet.getString("msg_id"));
                ofMessage.setMsg_type(resultSet.getString("msg_type"));
                ofMessage.setMsg_from(resultSet.getString("msg_from"));
                ofMessage.setMsg_to(resultSet.getString("msg_to"));
                ofMessage.setMsg_time(resultSet.getString("msg_time"));
                ofMessage.setBody(MessageUtils.decode(resultSet.getString("body")));
                ofMessage.setSession_id(resultSet.getString("session_id"));
                ofMessage.setMsg_status(resultSet.getInt("msg_status"));
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
