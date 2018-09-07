package com.itonglian.dao.impl;

import com.itonglian.dao.SessionDao;
import com.itonglian.entity.OfSession;
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

public class SessionDaoImpl implements SessionDao {

    private static final SessionDao sessionDao = new SessionDaoImpl();

    private static final String INSERT = "INSERT INTO ofsession (session_id,session_type,session_name,session_create_time,session_modify_time,session_delete_time,session_valid,session_user) VALUES(?,?,?,?,?,?,?,?)";

    private static final String UPDATE_NAME_BY_ID = "UPDATE ofsession SET session_name = ?,session_modify_time = ? WHERE session_id = ?";

    private static final String DELETE = "DELETE FROM ofsession WHERE session_id = ?";

    private static final Logger Log = LoggerFactory.getLogger(ChatDaoImpl.class);

    private static final String QUERY_BY_ID = "SELECT * FROM ofsession WHERE session_id = ?";

    private static final String FIND_SESSIONS_BY_USER = "SELECT A.* FROM ofsession A,ofsubscriber B WHERE A.session_id = B.session_id AND B.user_id = ? AND A.session_valid = ?";

    private static final String UPDATE_PIC = "UPDATE ofsession SET session_pic=? WHERE session_id=?";

    private static final String MODIFY = "UPDATE ofsession SET session_modify_time = ? WHERE session_id = ?";

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
            preparedStatement.setString(i++,session.getSession_id());
            preparedStatement.setInt(i++,session.getSession_type());
            preparedStatement.setString(i++,session.getSession_name());
            preparedStatement.setString(i++,session.getSession_create_time());
            preparedStatement.setString(i++,session.getSession_modify_time());
            preparedStatement.setString(i++,session.getSession_delete_time());
            preparedStatement.setInt(i++,session.getSession_valid());
            preparedStatement.setString(i++,session.getSession_user());
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
    public void updateNameById(String sessionId, String sessionName,String modifyTime) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_NAME_BY_ID);
            preparedStatement.setString(1,sessionName);
            preparedStatement.setString(2,modifyTime);
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
                ofSession.setSession_id(resultSet.getString("session_id"));
                ofSession.setSession_name(resultSet.getString("session_name"));
                ofSession.setSession_type(resultSet.getInt("session_type"));
                ofSession.setSession_user(resultSet.getString("session_user"));
                ofSession.setSession_valid(resultSet.getInt("session_valid"));
                ofSession.setSession_create_time(resultSet.getString("session_create_time"));
                ofSession.setSession_delete_time(resultSet.getString("session_delete_time"));
                ofSession.setSession_modify_time(resultSet.getString("session_modify_time"));
                ofSession.setSession_pic(resultSet.getString("session_pic"));
                return ofSession;
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public List<OfSession> findSessionsByUser(String userId,int valid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfSession> ofSessions = new ArrayList<OfSession>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_SESSIONS_BY_USER);
            preparedStatement.setString(1,userId);
            preparedStatement.setInt(2,valid);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfSession ofSession = new OfSession();
                ofSession.setSession_id(resultSet.getString("session_id"));
                ofSession.setSession_name(resultSet.getString("session_name"));
                ofSession.setSession_type(resultSet.getInt("session_type"));
                ofSession.setSession_user(resultSet.getString("session_user"));
                ofSession.setSession_valid(resultSet.getInt("session_valid"));
                ofSession.setSession_create_time(resultSet.getString("session_create_time"));
                ofSession.setSession_delete_time(resultSet.getString("session_delete_time"));
                ofSession.setSession_modify_time(resultSet.getString("session_modify_time"));
                ofSession.setSession_pic(resultSet.getString("session_pic"));
                ofSessions.add(ofSession);
            }
            return ofSessions;
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return null;
    }


    @Override
    public void updatePic(String sessionId, String sessionPic) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PIC);
            preparedStatement.setString(1,sessionPic);
            preparedStatement.setString(2,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void modify(String sessionId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(MODIFY);
            preparedStatement.setString(1,MessageUtils.getTs());
            preparedStatement.setString(2,sessionId);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }


}
