package com.itonglian.utils;

import com.itonglian.mapper.mysql.*;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.JdbcType;
import org.jivesoftware.util.JiveGlobals;

import javax.sql.DataSource;

public class MyBatisSessionFactory {

    private static SqlSessionFactory sqlSessionFactory;

    private static class MyBatisSessionFactoryHolder{
        private static MyBatisSessionFactory myBatisSessionFactory=new MyBatisSessionFactory();
    }

    public static MyBatisSessionFactory getInstance(){

        return MyBatisSessionFactoryHolder.myBatisSessionFactory;
    }

    private MyBatisSessionFactory(){
        DataSource dataSource = new DruidDataSourceFactory().getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(MessageMapper.class);
        configuration.addMapper(ChatMapper.class);
        configuration.addMapper(SessionMapper.class);
        configuration.addMapper(com.itonglian.mapper.sqlserver.SessionMapper.class);
        configuration.addMapper(com.itonglian.mapper.sqlserver.OfflineMapper.class);
        configuration.addMapper(SubscriberMapper.class);
        configuration.addMapper(OfflineMapper.class);
        configuration.addMapper(PubactMapper.class);
        configuration.addMapper(com.itonglian.mapper.sqlserver.PubactMapper.class);
        configuration.addMapper(StatusMapper.class);
        configuration.addMapper(com.itonglian.mapper.sqlserver.StatusMapper.class);
        configuration.addMapper(StyleMapper.class);
        configuration.addMapper(UserMapper.class);
        configuration.addMapper(com.itonglian.mapper.sqlserver.MessageMapper.class);
        configuration.addMapper(com.itonglian.mapper.oracle.MessageMapper.class);
        configuration.addMapper(com.itonglian.mapper.oracle.StatusMapper.class);
        configuration.setCacheEnabled(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        String driver = JiveGlobals.getXMLProperty("database.defaultProvider.driver");
        switch (driver){
            case "oracle.jdbc.driver.OracleDriver":
                configuration.setDatabaseId("Oracle");
                break;
            case "net.sourceforge.jtds.jdbc.Driver":
                configuration.setDatabaseId("SQLServer");
                break;
            default:
                configuration.setDatabaseId("MySQL");
                break;

        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSessionFactory createSessionFactory(){
        return sqlSessionFactory;
    }
}
