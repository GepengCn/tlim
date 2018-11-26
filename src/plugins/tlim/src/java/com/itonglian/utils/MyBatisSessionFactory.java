package com.itonglian.utils;

import com.itonglian.mapper.mysql.*;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class MyBatisSessionFactory {

    private static final Logger Log = LoggerFactory.getLogger(MyBatisSessionFactory.class);

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
        configuration.addMapper(SubscriberMapper.class);
        configuration.addMapper(OfflineMapper.class);
        configuration.addMapper(PubactMapper.class);
        configuration.addMapper(StatusMapper.class);
        configuration.addMapper(StyleMapper.class);
        configuration.addMapper(UserMapper.class);
        configuration.addMapper(com.itonglian.mapper.oracle.MessageMapper.class);
        configuration.setLazyLoadingEnabled(true);
        configuration.setCacheEnabled(true);
        String driver = JiveGlobals.getXMLProperty("database.defaultProvider.driver");
        switch (driver){
            case "oracle.jdbc.driver.OracleDriver":
                configuration.setDatabaseId("Oracle");
                break;
            case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
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
