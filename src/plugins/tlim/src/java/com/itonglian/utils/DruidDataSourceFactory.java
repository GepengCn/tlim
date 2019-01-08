package com.itonglian.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

public class DruidDataSourceFactory implements DataSourceFactory {

    private static final Logger Log = LoggerFactory.getLogger(DruidDataSourceFactory.class);


    private Properties props;

    @Override
    public void setProperties(Properties properties) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        String driver = JiveGlobals.getXMLProperty("database.defaultProvider.driver");
        String url = JiveGlobals.getXMLProperty("database.defaultProvider.serverURL");
        if("net.sourceforge.jtds.jdbc.Driver".equals(driver)){
            driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String srcUrl = url;
            String host = srcUrl.substring(srcUrl.indexOf("//")+2,srcUrl.lastIndexOf("/"));
            String databaseName = srcUrl.substring(srcUrl.lastIndexOf("/")+1,srcUrl.lastIndexOf(";"));
            url = "jdbc:sqlserver://"+host+";DatabaseName="+databaseName;
        }

        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(driver);
        dds.setUrl(url);
        dds.setUsername(JiveGlobals.getXMLProperty("database.defaultProvider.username"));
        dds.setPassword(JiveGlobals.getXMLProperty("database.defaultProvider.password"));
        dds.setMaxActive(XMLProperties.getMaxActive());
        dds.setAsyncInit(XMLProperties.getAsyncInit());
        dds.setInitialSize(XMLProperties.getInitialSize());
        dds.setMinIdle(XMLProperties.getMinIdle());
        dds.setMaxWait(XMLProperties.getMaxWait());
        dds.setTimeBetweenEvictionRunsMillis(XMLProperties.getTimeBetweenEvictionRunsMillis());
        dds.setMinEvictableIdleTimeMillis(XMLProperties.getMinEvictableIdleTimeMillis());
        dds.setMaxEvictableIdleTimeMillis(XMLProperties.getMaxEvictableIdleTimeMillis());
        dds.setRemoveAbandoned(XMLProperties.getRemoveAbandoned());
        dds.setRemoveAbandonedTimeout(XMLProperties.getRemoveAbandonedTimeout());
        dds.setTestWhileIdle(XMLProperties.getTestWhileIdle());
        dds.setTestOnBorrow(XMLProperties.getTestOnBorrow());
        dds.setTestOnReturn(XMLProperties.getTestOnReturn());
        dds.setPoolPreparedStatements(XMLProperties.getPoolPreparedStatements());
        dds.setMaxOpenPreparedStatements(XMLProperties.getMaxOpenPreparedStatements());

        if("oracle.jdbc.driver.OracleDriver".equals(driver)){
            dds.setOracle(true);
        }
        // 其他配置可以根据MyBatis主配置文件进行配置
        try {
            dds.init();
        } catch (SQLException e) {
            Log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return dds;
    }
}
