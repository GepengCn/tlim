package com.itonglian.utils;

import com.itonglian.mapper.ChatMapper;
import com.itonglian.mapper.MessageMapper;
import com.itonglian.mapper.SessionMapper;
import com.itonglian.mapper.SubscriberMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class MyBatisSessionFactory {

    private static final Logger Log = LoggerFactory.getLogger(MyBatisSessionFactory.class);

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory createSessionFactory(){
        DataSource dataSource = new DruidDataSourceFactory().getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(MessageMapper.class);
        configuration.addMapper(ChatMapper.class);
        configuration.addMapper(SessionMapper.class);
        configuration.addMapper(SubscriberMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }
}
