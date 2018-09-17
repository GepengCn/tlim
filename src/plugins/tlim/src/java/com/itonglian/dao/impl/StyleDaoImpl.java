package com.itonglian.dao.impl;

import com.itonglian.dao.StyleDao;
import com.itonglian.entity.OfStyle;
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

public class StyleDaoImpl implements StyleDao {

    private static final String INSERT = "INSERT INTO ofstyle (style_id,style_name,style_value,user_id) VALUES(?,?,?,?)";

    private static final String QUERY_BY_ID = "SELECT * FROM ofstyle WHERE user_id = ?";

    private static final Logger Log = LoggerFactory.getLogger(StyleDaoImpl.class);

    private static final StyleDao styleDao = new StyleDaoImpl();

    public static StyleDao getInstance(){
        return styleDao;
    }

    @Override
    public void add(OfStyle ofStyle) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT);
            int i=1;
            preparedStatement.setLong(i++,SequenceManager.nextID(OfStyle.ID_Contants.STYLE_KEY));
            preparedStatement.setString(i++,ofStyle.getStyle_name());
            preparedStatement.setInt(i++,ofStyle.getStyle_value());
            preparedStatement.setString(i++,ofStyle.getUser_id());
            preparedStatement.execute();
        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(preparedStatement,connection);
        }
    }

    @Override
    public  List<OfStyle> query(String user_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OfStyle> ofStyleList = new ArrayList<>();
        try {
            connection = DbConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(QUERY_BY_ID);
            preparedStatement.setString(1,user_id);
            resultSet = preparedStatement.executeQuery();
            OfStyle ofStyle = new OfStyle();
            while(resultSet.next()){
                ofStyle.setStyle_id(resultSet.getLong("style_id"));
                ofStyle.setStyle_name(resultSet.getString("style_name"));
                ofStyle.setStyle_value(resultSet.getInt("style_value"));
                ofStyle.setUser_id(resultSet.getString("user_id"));
                ofStyleList.add(ofStyle);
            }

        }catch (Exception e){
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }finally {
            DbConnectionManager.closeConnection(resultSet,preparedStatement,connection);
        }
        return ofStyleList;
    }
}
