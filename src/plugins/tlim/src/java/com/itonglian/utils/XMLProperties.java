package com.itonglian.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XMLProperties {

    private static final boolean USER_ASYNC = true;

    private static final int USER_ASYNC_INTERVAL = 1;

    private static final String MASTER_SECRET = "83a8c468321366eb977c61f2";

    private static final String APP_KEY = "90fd74bf44097c9bb69c3fd1";

    private static final int DRUID_MAX_ACTIVE = 5000;

    private static final int DRUID_INITIAL_SIZE = 100;

    private static final int DRUID_MIN_IDLE = 80;

    private static final int DRUID_MAX_WAIT = 10000;

    private static final int DRUID_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 30000;

    private static final int DRUID_MIN_EVICTABLE_IDLE_TIME_MILLIS = 300000;

    private static final int DRUID_MAX_EVICTABLE_IDLE_TIME_MILLIS = 600000;

    private static final boolean DRUID_REMOVE_ABANDONED = true;

    private static final int DRUID_REMOVE_ABANDONED_TIMEOUT = 80;

    private static final boolean DRUID_TEST_WHILE_IDLE = true;

    private static final boolean DRUID_TEST_ON_BORROW = false;

    private static final boolean DRUID_TEST_ON_RETURN = false;

    private static final boolean DRUID_POOL_PREPARED_STATEMENTS = true;

    private static final int DRUID_MAX_OPEN_PREPARED_STATEMENTS = 50;

    private static final boolean DRUID_ASYNC_INIT = true;

    private static final boolean NETTY_SERVER = false;

    private static final boolean NETTY_CLIENT = false;

    private static final int NETTY_SERVER_PORT = 9599;

    private static final String NETTY_CLIENT_IP = "127.0.0.1";

    private static final int NETTY_CLIENT_PORT = 9599;

    private static final int HTTP_OBJECT_AGGREGATOR_VALUE = 10*1024*1024;

    private static final int OPTION_SO_BACKLOG = 128;

    private static final String CHANNEL_CODE = "";

    private static volatile ConcurrentHashMap<String,Object> concurrentHashMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(XMLProperties.class);


    static{
        concurrentHashMap.put("tlim.nettyServer",NETTY_SERVER);
        concurrentHashMap.put("nettyClient",NETTY_CLIENT);
        concurrentHashMap.put("nettyServerPort",NETTY_SERVER_PORT);
        concurrentHashMap.put("tlim.nettyClientIp",NETTY_CLIENT_IP);
        concurrentHashMap.put("tlim.nettyClientPort",NETTY_CLIENT_PORT);
        concurrentHashMap.put("tlim.channelCode",CHANNEL_CODE);
        concurrentHashMap.put("tlim.httpObjectAggregatorValue",HTTP_OBJECT_AGGREGATOR_VALUE);
        concurrentHashMap.put("tlim.optionSoBacklog",OPTION_SO_BACKLOG);
        concurrentHashMap.put("tlim.userAsync",USER_ASYNC);
        concurrentHashMap.put("tlim.userAsyncInterval",USER_ASYNC_INTERVAL);
        concurrentHashMap.put("tlim.masterSecret",MASTER_SECRET);
        concurrentHashMap.put("tlim.appKey",APP_KEY);
        concurrentHashMap.put("tlim.druid.maxActive",DRUID_MAX_ACTIVE);
        concurrentHashMap.put("tlim.druid.asyncInit",DRUID_ASYNC_INIT);
        concurrentHashMap.put("tlim.druid.initialSize",DRUID_INITIAL_SIZE);
        concurrentHashMap.put("tlim.druid.minIdle",DRUID_MIN_IDLE);
        concurrentHashMap.put("tlim.druid.maxWait",DRUID_MAX_WAIT);
        concurrentHashMap.put("tlim.druid.timeBetweenEvictionRunsMillis",DRUID_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        concurrentHashMap.put("tlim.druid.minEvictableIdleTimeMillis",DRUID_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        concurrentHashMap.put("tlim.druid.maxEvictableIdleTimeMillis",DRUID_MAX_EVICTABLE_IDLE_TIME_MILLIS);
        concurrentHashMap.put("tlim.druid.removeAbandoned",DRUID_REMOVE_ABANDONED);
        concurrentHashMap.put("tlim.druid.removeAbandonedTimeout",DRUID_REMOVE_ABANDONED_TIMEOUT);
        concurrentHashMap.put("tlim.druid.testWhileIdle",DRUID_TEST_WHILE_IDLE);
        concurrentHashMap.put("tlim.druid.testOnBorrow",DRUID_TEST_ON_BORROW);
        concurrentHashMap.put("tlim.druid.testOnReturn",DRUID_TEST_ON_RETURN);
        concurrentHashMap.put("tlim.druid.poolPreparedStatements",DRUID_POOL_PREPARED_STATEMENTS);
        concurrentHashMap.put("tlim.druid.maxOpenPreparedStatements",DRUID_MAX_OPEN_PREPARED_STATEMENTS);
    }

    public static ConcurrentHashMap<String,Object> getConcurrentHashMap(){
        return concurrentHashMap;
    }

    public static boolean getNettyServer(){
        return getBooleanValue("tlim.nettyServer");
    }
    public static boolean getNettyClient(){
        return getBooleanValue("tlim.nettyClient");
    }
    public static int getNettyServerPort(){
        return getIntegerValue("tlim.nettyServerPort");
    }

    public static String getNettyClientIp(){
        return getStringValue("tlim.nettyClientIp");
    }

    public static int getNettyClientPort(){
        return getIntegerValue("tlim.nettyClientPort");
    }

    public static String getChannelCode(){
        String channelCode = getStringValue("tlim.channelCode");
        if(StringUtils.isNullOrEmpty(channelCode)){
            return "";
        }
        return "/"+channelCode;
    }

    public static int getHttpObjectAggregatorValue(){
        return getIntegerValue("tlim.httpObjectAggregatorValue");
    }

    public static int getOptionSoBacklog(){
        return getIntegerValue("tlim.optionSoBacklog");
    }

    public static boolean getUserAsync(){
        return getBooleanValue("tlim.userAsync");
    }

    public static int getUserASyncInterval(){
        return getIntegerValue("tlim.userAsyncInterval");
    }


    public static String getMasterSecret(){
        return getStringValue("tlim.masterSecret");

    }

    public static String getAppKey(){
        return getStringValue("tlim.appKey");
    }

    public static int getMaxActive(){
        return getIntegerValue("tlim.druid.maxActive");
    }

    public static boolean getAsyncInit(){
        return getBooleanValue("tlim.druid.asyncInit");
    }
    public static int getInitialSize(){
        return getIntegerValue("tlim.druid.initialSize");
    }

    public static int getMinIdle(){
        return getIntegerValue("tlim.druid.minIdle");
    }

    public static int getMaxWait(){
        return getIntegerValue("tlim.druid.maxWait");
    }

    public static int getTimeBetweenEvictionRunsMillis(){
        return getIntegerValue("tlim.druid.timeBetweenEvictionRunsMillis");
    }

    public static int getMinEvictableIdleTimeMillis(){
        return getIntegerValue("tlim.druid.minEvictableIdleTimeMillis");
    }

    public static int getMaxEvictableIdleTimeMillis(){
        return getIntegerValue("tlim.druid.maxEvictableIdleTimeMillis");
    }

    public static boolean getRemoveAbandoned(){
        return getBooleanValue("tlim.druid.removeAbandoned");
    }

    public static int getRemoveAbandonedTimeout(){
        return getIntegerValue("tlim.druid.removeAbandonedTimeout");
    }

    public static boolean getTestWhileIdle(){
        return getBooleanValue("tlim.druid.testWhileIdle");
    }

    public static boolean getTestOnBorrow(){
        return getBooleanValue("tlim.druid.testOnBorrow");
    }

    public static boolean getTestOnReturn(){
        return getBooleanValue("tlim.druid.testOnReturn");
    }

    public static boolean getPoolPreparedStatements(){
        return getBooleanValue("tlim.druid.poolPreparedStatements");
    }

    public static int getMaxOpenPreparedStatements(){
        return getIntegerValue("tlim.druid.maxOpenPreparedStatements");
    }

    private static String getStringValue(String xmlProperty){
        String value = (String)concurrentHashMap.get(xmlProperty);
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            value = temp;
        }
        return value;
    }
    private static int getIntegerValue(String xmlProperty){
        int value = (int)concurrentHashMap.get(xmlProperty);
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            value = Integer.valueOf(temp);
        }
        return value;
    }

    private static boolean getBooleanValue(String xmlProperty){
        boolean value = (boolean)concurrentHashMap.get(xmlProperty);
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            value = Boolean.parseBoolean(temp);
        }
        return value;
    }


    public static void print(){
        for(Map.Entry<String, Object> entry:concurrentHashMap.entrySet()){
            logger.info(entry.getKey()+" - "+entry.getValue());
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property{

        private String name;

        private Object value;

    }
}
