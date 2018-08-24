package com.itonglian.dao.impl;

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

    private static final String UPDATE = "UPDATE ofstatus SET status=? WHERE session_id=? AND status=?";

    private static final String QUERY_UNREAD = "SELECT session_id,count(*) unReadNum FROM ofstatus WHERE msg_to=?  AND status = ? GROUP BY session_id";

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
    public void update(String session_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            int i=1;
            preparedStatement.setInt(i++,1);
            preparedStatement.setString(i++,session_id);
            preparedStatement.setInt(i++,0);
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
}
