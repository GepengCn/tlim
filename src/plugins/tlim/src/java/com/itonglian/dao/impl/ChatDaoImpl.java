package com.itonglian.dao.impl;

import com.itonglian.dao.ChatDao;
import com.itonglian.dao.StatusDao;
import com.itonglian.entity.OfChat;
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
import java.util.Map;

public class ChatDaoImpl implements ChatDao {

    private static final ChatDao chatDao = new ChatDaoImpl();

    private static final StatusDao statusDao = StatusDaoImpl.getInstance();

    private static final String INSERT = "INSERT INTO ofmessage (msg_id,msg_type,msg_from,msg_to,msg_time,body) VALUES(?,?,?,?,?,?)";

    private static final String DELETE = "DELETE FROM ofmessage WHERE msg_id=?";

    private static final String DELETE_OFFLINE = "DELETE FROM ofOffline WHERE messageID = ?";

    private static final String DELETE_OFFLINE_BY_SESSION = "DELETE FROM ofOffline WHERE stanza like ? ";


    private static final String QUERY_BY_ID = "SELECT * FROM ofmessage WHERE msg_id = ?";

    private static final String INSERT_WITH_SESS = "INSERT INTO ofmessage (id_,msg_id,msg_type,msg_from,msg_to,msg_time,body,session_id) VALUES(?,?,?,?,?,?,?,?)";

    private static final String INSERT_CHAT = "INSERT INTO ofchat(chat_id,chat_name,chat_user,chat_other,chat_create_time,chat_modify_time,chat_pic) VALUES(?,?,?,?,?,?,?)";

    private static final String IS_EXIST = "SELECT count(*) AS total FROM ofmessage WHERE msg_id=?";

    private static final String CHAT_LIST = "SELECT * FROM ofchat WHERE chat_other = ?";

    private static final String IS_EXIST_CHAT = "SELECT * FROM ofchat WHERE chat_user = ? AND chat_other = ?";

    private static final String MODIFY = "UPDATE ofchat SET chat_modify_time = ? WHERE chat_user = ? AND chat_other = ?";

    private static final String CLEAR_CHAT_HISTORY = "DELETE FROM ofmessage  WHERE msg_from = ? AND msg_to = ?";


    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static ChatDao getInstance(){
        return chatDao;
    }

    public void addThenSend(OfMessage ofMessage){
        int isExist = this.isExist(ofMessage.getMsg_id());
        if(isExist>0||isExist==-1){
            return;
        }
        /*String msg_type = ofMessage.getMsg_type();
        String isCommand = msg_type.split("-")[1].substring(0,1);
        if(!ofMessage.getMsg_from().equals(ofMessage.getMsg_to())&&"0".equals(isCommand)){
            OfStatus ofStatus = new OfStatus();
            ofStatus.setMsg_id(ofMessage.getMsg_id());
            ofStatus.setMsg_to(ofMessage.getMsg_to());
            ofStatus.setMsg_type(ofMessage.getMsg_type());
            ofStatus.setSession_id(ofMessage.getSession_id());
            ofStatus.setStatus(0);
            statusDao.add(ofStatus);
        }*/
        this.add(ofMessage);

    }

    @Override
    public void add(OfMessage ofMessage) {
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
    public int isExist(String msgId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = -1;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(IS_EXIST);
            int i=1;
            preparedStatement.setString(i++,msgId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                result = resultSet.getInt("total");
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return result;
    }



    @Override
    public List<OfChat> chatList(String userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfChat> ofChats = new ArrayList<OfChat>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CHAT_LIST);
            preparedStatement.setString(1,userId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfChat ofChat = new OfChat();
                ofChat.setChat_id(resultSet.getString("chat_id"));
                ofChat.setChat_name(resultSet.getString("chat_name"));
                ofChat.setChat_user(resultSet.getString("chat_user"));
                ofChat.setChat_other(resultSet.getString("chat_other"));
                ofChat.setChat_create_time(resultSet.getString("chat_create_time"));
                ofChat.setChat_modify_time(resultSet.getString("chat_modify_time"));
                ofChats.add(ofChat);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return ofChats;
    }

    @Override
    public boolean isExistChat(String msg_from,String msg_to) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(IS_EXIST_CHAT);
            preparedStatement.setString(1,msg_from);
            preparedStatement.setString(2,msg_to);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return false;
    }

    @Override
    public void add(OfChat ofChat) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_CHAT);
            int i=1;
            preparedStatement.setString(i++,ofChat.getChat_id());
            preparedStatement.setString(i++,ofChat.getChat_name());
            preparedStatement.setString(i++,ofChat.getChat_user());
            preparedStatement.setString(i++,ofChat.getChat_other());
            preparedStatement.setString(i++,ofChat.getChat_create_time());
            preparedStatement.setString(i++,ofChat.getChat_modify_time());
            preparedStatement.setString(i++,ofChat.getChat_pic());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }

    }

    @Override
    public void modify(String chat_user,String chat_other) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(MODIFY);
            preparedStatement.setString(1,MessageUtils.getTs());
            preparedStatement.setString(2,chat_user);
            preparedStatement.setString(3,chat_other);

            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void deleteOffline(String messageId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_OFFLINE);
            int i=1;
            preparedStatement.setString(i++,messageId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void deleteOfflineBySession(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_OFFLINE_BY_SESSION);
            int i=1;
            preparedStatement.setString(i++,"%"+sessionId+"%");
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
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

}
