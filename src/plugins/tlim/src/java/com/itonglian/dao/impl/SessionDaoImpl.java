package com.itonglian.dao.impl;

import com.itonglian.dao.SessionDao;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.OfSession;
import com.itonglian.utils.MessageUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.database.DbConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SessionDaoImpl implements SessionDao {

    private static final SessionDao sessionDao = new SessionDaoImpl();

    private static final String INSERT = "INSERT INTO ofsession (session_id,session_type,session_name,session_create_time,session_modify_time,session_delete_time,session_valid,session_user) VALUES(?,?,?,?,?,?,?,?)";

    private static final String UPDATE_NAME_BY_ID = "UPDATE ofsession SET session_name = ?,session_modify_time = ? WHERE session_id = ?";

    private static final String DELETE = "DELETE FROM ofsession WHERE session_id = ?";

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    private static final String QUERY_BY_ID = "SELECT * FROM ofsession WHERE session_id = ?";

    public static SessionDao getInstance(){
        return sessionDao;
    }


    @Override
    public void add(OfSession session) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT);
            int i=1;
            preparedStatement.setString(i++,session.getSessionId());
            preparedStatement.setInt(i++,session.getSessionType());
            preparedStatement.setString(i++,session.getSessionName());
            preparedStatement.setString(i++,session.getSessionCreateTime());
            preparedStatement.setString(i++,session.getSessionModifyTime());
            preparedStatement.setString(i++,session.getSessionDeleteTime());
            preparedStatement.setInt(i++,session.getSessionValid());
            preparedStatement.setString(i++,session.getSessionUser());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void delete(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void update(OfSession session) {

    }

    @Override
    public void updateNameById(String sessionId, String sessionName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_NAME_BY_ID);
            preparedStatement.setString(1,sessionName);
            preparedStatement.setString(2,MessageUtils.getTs());
            preparedStatement.setString(3,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public OfSession findEntityById(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_BY_ID);
            preparedStatement.setString(1,sessionId);
            resultSet = preparedStatement.executeQuery();
            OfSession ofSession = new OfSession();
            if(resultSet.next()){
                ofSession.setSessionId(resultSet.getString("session_id"));
                ofSession.setSessionName(resultSet.getString("session_name"));
                ofSession.setSessionType(resultSet.getInt("session_type"));
                ofSession.setSessionUser(resultSet.getString("session_user"));
                ofSession.setSessionValid(resultSet.getInt("session_valid"));
                ofSession.setSessionCreateTime(resultSet.getString("session_create_time"));
                ofSession.setSessionDeleteTime(resultSet.getString("session_delete_time"));
                ofSession.setSessionModifyTime(resultSet.getString("session_modify_time"));
                return ofSession;
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }


}
