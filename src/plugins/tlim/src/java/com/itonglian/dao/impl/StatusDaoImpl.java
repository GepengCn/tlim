package com.itonglian.dao.impl;

import com.itonglian.bean.SessionRead;
import com.itonglian.bean.SessionUnread;
import com.itonglian.dao.StatusDao;
import com.itonglian.entity.OfStatus;
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

public class StatusDaoImpl implements StatusDao {

    private static final StatusDao statusDao = new StatusDaoImpl();

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    public static StatusDao getInstance(){
        return statusDao;
    }

    private static final String INSERT = "INSERT INTO ofstatus (id_,msg_id,msg_to,msg_type,status,session_id) values(?,?,?,?,?,?)";

    private static final String UPDATE = "UPDATE ofstatus SET status=? WHERE session_id=? AND status=? AND msg_to=?";

    private static final String QUERY = "SELECT * FROM ofstatus WHERE session_id=? AND status=? AND msg_to=?";

    private static final String DELETE = "DELETE from ofstatus WHERE session_id=? AND msg_to=?";

    private static final String QUERY_UNREAD = "SELECT session_id,count(*) unReadNum FROM ofstatus WHERE msg_to=?  AND status = ? GROUP BY session_id";

    private static final String FIND_MSG_READ = "SELECT msg_id,count(1) readNum from ofstatus  WHERE  status = ? AND session_id = ? GROUP BY msg_id ";

    private static final String FIND_CHAT_MSG_READ = "SELECT msg_id,count(1) readNum from ofstatus  WHERE  status = ? AND session_id = ? AND msg_to = ? GROUP BY msg_id ";

    private static final String FIND_MSG_STATUS_LIST = "SELECT * FROM ofstatus WHERE msg_id = ?";

    private static final String READ_OR_NOT = "SELECT * FROM ofstatus WHERE msg_id = ? AND msg_to = ?";

    private static final String UPDATE_BY_MSG_ID = "UPDATE ofstatus SET status=? WHERE msg_id=? AND msg_to=?";

    @Override
    public void add(OfStatus ofStatus) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT);
            int i=1;
            preparedStatement.setLong(i++,SequenceManager.nextID(OfStatus.ID_Contants.STATUS_KEY));
            preparedStatement.setString(i++,ofStatus.getMsg_id());
            preparedStatement.setString(i++,ofStatus.getMsg_to());
            preparedStatement.setString(i++,ofStatus.getMsg_type());
            preparedStatement.setInt(i++,ofStatus.getStatus());
            preparedStatement.setString(i++,ofStatus.getSession_id());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void update(String session_id,String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            int i=1;
            preparedStatement.setInt(i++,1);
            preparedStatement.setString(i++,session_id);
            preparedStatement.setInt(i++,0);
            preparedStatement.setString(i++,msg_to);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public List<OfStatus> query(String session_id, String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfStatus> ofStatuses = new ArrayList<OfStatus>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setInt(i++,0);
            preparedStatement.setString(i++,msg_to);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfStatus ofStatus = new OfStatus();
                ofStatus.setMsg_id(resultSet.getString("msg_id"));
                ofStatus.setMsg_type(resultSet.getString("msg_type"));
                ofStatus.setMsg_to(resultSet.getString("msg_to"));
                ofStatus.setSession_id(resultSet.getString("session_id"));
                ofStatus.setStatus(resultSet.getInt("status"));
                ofStatuses.add(ofStatus);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return ofStatuses;
    }

    @Override
    public void delete(String session_id,String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            int i=1;
            preparedStatement.setString(i++,session_id);
            preparedStatement.setString(i++,msg_to);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public List<SessionUnread> findUnread(String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SessionUnread> sessionUnreads = new ArrayList<SessionUnread>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_UNREAD);
            preparedStatement.setString(1,msg_to);
            preparedStatement.setInt(2,0);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SessionUnread sessionUnread = new SessionUnread();
                sessionUnread.setSession_id(resultSet.getString("session_id"));
                sessionUnread.setUnReadNum(resultSet.getInt("unReadNum"));
                sessionUnreads.add(sessionUnread);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return sessionUnreads;
    }

    @Override
    public List<SessionRead> findMsgRead(String session_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SessionRead> sessionReads = new ArrayList<SessionRead>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_MSG_READ);
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2,session_id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SessionRead sessionRead = new SessionRead();
                sessionRead.setMsg_id(resultSet.getString("msg_id"));
                sessionRead.setReadNum(resultSet.getInt("readNum"));
                sessionReads.add(sessionRead);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return sessionReads;
    }

    @Override
    public List<SessionRead> findChatMsgRead(String session_id, String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SessionRead> sessionReads = new ArrayList<SessionRead>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CHAT_MSG_READ);
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2,session_id);
            preparedStatement.setString(3,msg_to);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                SessionRead sessionRead = new SessionRead();
                sessionRead.setMsg_id(resultSet.getString("msg_id"));
                sessionRead.setReadNum(resultSet.getInt("readNum"));
                sessionReads.add(sessionRead);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return sessionReads;
    }

    @Override
    public List<OfStatus> findMsgStatusList(String msg_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfStatus> ofStatuses = new ArrayList<OfStatus>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_MSG_STATUS_LIST);
            preparedStatement.setString(1,msg_id);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfStatus ofStatus = new OfStatus();
                ofStatus.setMsg_id(resultSet.getString("msg_id"));
                ofStatus.setMsg_type(resultSet.getString("msg_type"));
                ofStatus.setMsg_to(resultSet.getString("msg_to"));
                ofStatus.setSession_id(resultSet.getString("session_id"));
                ofStatus.setStatus(resultSet.getInt("status"));
                ofStatuses.add(ofStatus);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return ofStatuses;
    }

    @Override
    public int readOrNot(String msg_id,String msg_to) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(READ_OR_NOT);
            preparedStatement.setString(1,msg_id);
            preparedStatement.setString(2,msg_to);
            resultSet = preparedStatement.executeQuery();
            OfStatus ofStatus = new OfStatus();
            if(resultSet.next()){
                ofStatus.setMsg_id(resultSet.getString("msg_id"));
                ofStatus.setMsg_type(resultSet.getString("msg_type"));
                ofStatus.setMsg_to(resultSet.getString("msg_to"));
                ofStatus.setSession_id(resultSet.getString("session_id"));
                ofStatus.setStatus(resultSet.getInt("status"));
                return ofStatus.getStatus();
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return -1;
    }

    @Override
    public void updateByMsgId(String msg_id, String msg_to,int status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_BY_MSG_ID);
            int i=1;
            preparedStatement.setInt(i++,status);
            preparedStatement.setString(i++,msg_id);
            preparedStatement.setString(i++,msg_to);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

}
