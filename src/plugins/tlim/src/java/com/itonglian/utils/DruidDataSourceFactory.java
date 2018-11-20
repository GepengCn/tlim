package com.itonglian.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.jivesoftware.util.JiveGlobals;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties properties) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(JiveGlobals.getXMLProperty("database.defaultProvider.driver"));
        dds.setUrl(JiveGlobals.getXMLProperty("database.defaultProvider.serverURL"));
        dds.setUsername(JiveGlobals.getXMLProperty("database.defaultProvider.username"));
        dds.setPassword(JiveGlobals.getXMLProperty("database.defaultProvider.password"));
        dds.setMaxActive(5000);
        dds.setAsyncInit(true);
        dds.setInitialSize(100);
        dds.setMinIdle(80);
        dds.setMaxWait(10000);
        dds.setTimeBetweenEvictionRunsMillis(30000);
        dds.setMinEvictableIdleTimeMillis(300000);
        dds.setMaxEvictableIdleTimeMillis(600000);
        dds.setRemoveAbandoned(true);
        dds.setRemoveAbandonedTimeout(80);
        dds.setTestWhileIdle(true);
        dds.setTestOnBorrow(false);
        dds.setTestOnReturn(false);
        dds.setPoolPreparedStatements(true);
        dds.setMaxOpenPreparedStatements(50);
        // 其他配置可以根据MyBatis主配置文件进行配置
        try {
            dds.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dds;
    }
}
