package com.itonglian.dao.impl;

import com.itonglian.dao.UserDao;
import com.itonglian.entity.User;
import com.itonglian.utils.UserCacheManager;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {


    private static final UserDao userDao = new UserDaoImpl();

    private static final String QUERY_USER_LIST = "SELECT a.user_id,a.user_name,a.acct_login,b.user_email,b.pic_url,a.app_push_code FROM isc_user a,isc_user_info b  WHERE a.user_id = b.user_id AND a.dr=? AND b.dr=? ";

    private static final String CLEAR = "delete from ofUser WHERE username <> ?";

    private static final String FIND_BY_ID = "SELECT app_push_code FROM isc_user WHERE user_id = ? AND dr = ?";

    private static final Logger Log = LoggerFactory.getLogger(UserDaoImpl.class);

    public static UserDao getInstance(){
        return userDao;
    }

    @Override
    public void syncUser() {
        UserManager userManager =XMPPServer.getInstance().getUserManager();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_USER_LIST);
            preparedStatement.setString(1,"N");
            preparedStatement.setString(2,"N");
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setUser_id(resultSet.getString("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setAcct_login(resultSet.getString("acct_login"));
                user.setUser_email(resultSet.getString("user_email"));
                user.setPic_url(resultSet.getString("pic_url"));
                user.setApp_push_code(resultSet.getString("app_push_code"));
                if(!userManager.isRegisteredUser(user.getUser_id())){
                    userManager.createUser(user.getUser_id(),"123",user.getUser_name(),user.getUser_email());
                }
                UserCacheManager.add(user);
            }


        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }

    }

    @Override
    public void clear() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CLEAR);
            preparedStatement.setString(1,"admin");
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public String findAppPushCodeByUserId(String userId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String appPushCode = "";
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,"N");
            if(resultSet.next()){
                appPushCode=resultSet.getString("app_push_code");
            }
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return appPushCode;
    }


}
