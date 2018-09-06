package com.itonglian.dao.impl;

import com.itonglian.dao.PubactDao;
import com.itonglian.entity.OfPubact;
import com.itonglian.entity.OfStatus;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
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

public class PubactDaoImpl implements PubactDao {


    private static final PubactDao pubactDao = new PubactDaoImpl();

    private static final String INSERT = "INSERT INTO ofpubact (id_,title,content,user_id,ts,session_id) values(?,?,?,?,?,?)";

    private static final String FIND_BY_SESSION = "SELECT * FROM ofpubact WHERE session_id = ?";

    private static final String UPDATE = "UPDATE ofpubact SET title = ?,content = ? WHERE id_ = ?";

    private static final String DELETE = "DELETE FROM ofpubact WHERE id_ = ? ";

    public static PubactDao getInstance(){
        return pubactDao;
    }

    private static final Logger Log = LoggerFactory.getLogger(PubactDaoImpl.class);

    @Override
    public void add(String title, String content, String user_id,String session_id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT);
            int i=1;
            preparedStatement.setLong(i++,SequenceManager.nextID(OfStatus.ID_Contants.STATUS_KEY));
            preparedStatement.setString(i++,title);
            preparedStatement.setString(i++,content);
            preparedStatement.setString(i++,user_id);
            preparedStatement.setString(i++,MessageUtils.getTs());
            preparedStatement.setString(i++,session_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }

    }

    @Override
    public List<OfPubact> findBySession(String session_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfPubact> ofPubactList = new ArrayList<OfPubact>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_SESSION);
            preparedStatement.setString(1,session_id);

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfPubact ofPubact = new OfPubact();
                ofPubact.setId_(resultSet.getLong("id_"));
                ofPubact.setTitle(resultSet.getString("title"));
                ofPubact.setContent(resultSet.getString("content"));
                ofPubact.setUser_id(resultSet.getString("user_id"));
                ofPubact.setSession_id(resultSet.getString("session_id"));
                ofPubact.setTs(resultSet.getString("ts"));
                ofPubactList.add(ofPubact);
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return ofPubactList;
    }

    @Override
    public void update(String id_, String title, String content) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            int i=1;
            preparedStatement.setString(i++,title);
            preparedStatement.setString(i++,content);
            preparedStatement.setLong(i++,StringUtils.stringToLong(id_));
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void delete(String id_) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE);
            int i=1;
            preparedStatement.setLong(i++,StringUtils.stringToLong(id_));
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }
}
