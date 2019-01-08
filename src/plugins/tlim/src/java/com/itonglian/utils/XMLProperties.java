package com.itonglian.utils;

import org.jivesoftware.util.JiveGlobals;

public class XMLProperties {

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

    public static int getUserSyncInterval(){
        return getIntegerValue("tlim.userAsyncInterval",USER_ASYNC_INTERVAL);
    }


    public static String getMasterSecret(){
        return getStringValue("tlim.masterSecret",MASTER_SECRET);

    }

    public static String getAppKey(){
        return getStringValue("tlim.appKey",APP_KEY);
    }

    public static int getMaxActive(){
        return getIntegerValue("tlim.druid.maxActive",DRUID_MAX_ACTIVE);
    }

    public static boolean getAsyncInit(){
        return getBooleanValue("tlim.druid.asyncInit",DRUID_ASYNC_INIT);
    }
    public static int getInitialSize(){
        return getIntegerValue("tlim.druid.initialSize",DRUID_INITIAL_SIZE);
    }

    public static int getMinIdle(){
        return getIntegerValue("tlim.druid.minIdle",DRUID_MIN_IDLE);
    }

    public static int getMaxWait(){
        return getIntegerValue("tlim.druid.maxWait",DRUID_MAX_WAIT);
    }

    public static int getTimeBetweenEvictionRunsMillis(){
        return getIntegerValue("tlim.druid.timeBetweenEvictionRunsMillis",DRUID_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
    }

    public static int getMinEvictableIdleTimeMillis(){
        return getIntegerValue("tlim.druid.minEvictableIdleTimeMillis",DRUID_MIN_EVICTABLE_IDLE_TIME_MILLIS);
    }

    public static int getMaxEvictableIdleTimeMillis(){
        return getIntegerValue("tlim.druid.maxEvictableIdleTimeMillis",DRUID_MAX_EVICTABLE_IDLE_TIME_MILLIS);
    }

    public static boolean getRemoveAbandoned(){
        return getBooleanValue("tlim.druid.removeAbandoned",DRUID_REMOVE_ABANDONED);
    }

    public static int getRemoveAbandonedTimeout(){
        return getIntegerValue("tlim.druid.removeAbandonedTimeout",DRUID_REMOVE_ABANDONED_TIMEOUT);
    }

    public static boolean getTestWhileIdle(){
        return getBooleanValue("tlim.druid.testWhileIdle",DRUID_TEST_WHILE_IDLE);
    }

    public static boolean getTestOnBorrow(){
        return getBooleanValue("tlim.druid.testOnBorrow",DRUID_TEST_ON_BORROW);
    }

    public static boolean getTestOnReturn(){
        return getBooleanValue("tlim.druid.testOnReturn",DRUID_TEST_ON_RETURN);
    }

    public static boolean getPoolPreparedStatements(){
        return getBooleanValue("tlim.druid.poolPreparedStatements",DRUID_POOL_PREPARED_STATEMENTS);
    }

    public static int getMaxOpenPreparedStatements(){
        return getIntegerValue("tlim.druid.maxOpenPreparedStatements",DRUID_MAX_OPEN_PREPARED_STATEMENTS);
    }

    private static String getStringValue(String xmlProperty,String defaults){
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            return temp;
        }
        return defaults;
    }
    private static int getIntegerValue(String xmlProperty,int defaults){
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            return Integer.parseInt(temp);
        }
        return defaults;
    }

    private static boolean getBooleanValue(String xmlProperty,boolean defaults){
        String temp = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(temp)){
            return Boolean.parseBoolean(temp);
        }
        return defaults;
    }

}
