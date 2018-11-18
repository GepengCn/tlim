package com.itonglian.dao.impl;

import com.itonglian.dao.StatusDao;
import com.itonglian.entity.OfChat;
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

    private static final String INSERT = "INSERT INTO ofstatus (id_,msg_id,reader,status) values(?,?,?,?)";

    private static final String UPDATE = "UPDATE ofstatus SET status=? WHERE msg_id=?";

    private static final String FIND_BY_READER = "SELECT * FROM ofstatus WHERE reader=?";

    private static final String DELETE = "DELETE FROM ofstatus WHERE msg_id=?";

    private static final String QUERY_UNREAD = "SELECT session_id,count(*) unReadNum FROM ofstatus WHERE msg_to=?  AND status = ? GROUP BY session_id";

    private static final String FIND_MSG_READ = "SELECT msg_id,count(1) readNum FROM ofstatus  WHERE  status = ? AND session_id = ? GROUP BY msg_id ";

    private static final String FIND_CHAT_MSG_READ = "SELECT msg_id,count(1) readNum FROM ofstatus  WHERE  status = ? AND session_id = ? AND msg_to = ? GROUP BY msg_id ";

    private static final String FIND_MSG_STATUS_LIST = "SELECT * FROM ofstatus WHERE msg_id = ?";

    private static final String READ_OR_NOT = "SELECT * FROM ofstatus WHERE msg_id = ? AND msg_to = ?";

    private static final String UPDATE_BY_MSG_ID = "UPDATE ofstatus SET status=? WHERE msg_id=? AND msg_to=?";

    private static final String HAS_UNREAD = "SELECT count(1) AS unReadNum FROM ofstatus WHERE msg_to = ?  AND status = 0";

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
            preparedStatement.setString(i++,ofStatus.getReader());
            preparedStatement.setInt(i++,ofStatus.getStatus());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public void update(String msg_id,int msg_status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE);
            int i=1;
            preparedStatement.setInt(i++,msg_status);
            preparedStatement.setString(i++,msg_id);
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public List<OfStatus> findByReader(String reader) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfStatus> ofStatuses = new ArrayList<OfStatus>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_READER);
            preparedStatement.setString(1,reader);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                OfStatus ofStatus = new OfStatus();
                ofStatus.setMsg_id(resultSet.getString("msg_id"));
                ofStatus.setReader(resultSet.getString("reader"));
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


}
