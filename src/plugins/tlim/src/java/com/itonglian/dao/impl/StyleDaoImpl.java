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

public class StyleDaoImpl implements StyleDao {

    private static final String INSERT = "INSERT INTO ofstyle (style_id,style_name,style_value,user_id) VALUES(?,?,?,?)";

    private static final Logger Log = LoggerFactory.getLogger(StyleDaoImpl.class);


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
}
