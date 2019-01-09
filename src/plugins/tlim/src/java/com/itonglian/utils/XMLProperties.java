package com.itonglian.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jivesoftware.util.JiveGlobals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

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

    private static final int NETTY_SERVER_PORT = 9599;

    private static final int HTTP_OBJECT_AGGREGATOR_VALUE = 10*1024*1024;

    private static Set<Property> properties = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(XMLProperties.class);

    public static Set<Property> getProperties(){
        return properties;
    }

    public static boolean getNettyServer(){
        return getBooleanValue("tlim.nettyServer",NETTY_SERVER);
    }
    public static int getNettyServerPort(){
        return getIntegerValue("tlim.nettyServerPort",NETTY_SERVER_PORT);
    }

    public static int getHttpObjectAggregatorValue(){
        return getIntegerValue("tlim.httpObjectAggregatorValue",HTTP_OBJECT_AGGREGATOR_VALUE);

    }
    public static boolean getUserAsync(){
        return getBooleanValue("tlim.userAsync",USER_ASYNC);
    }

    public static int getUserASyncInterval(){
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
        String realValue = defaults;
        String tempValue = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(tempValue)){
            realValue = tempValue;
        }
        addProperty(xmlProperty,realValue);
        return realValue;
    }
    private static int getIntegerValue(String xmlProperty,int defaults){
        int realValue = defaults;
        String tempValue = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(tempValue)){
            realValue = Integer.parseInt(tempValue);
        }
        addProperty(xmlProperty,realValue);
        return realValue;
    }

    private static boolean getBooleanValue(String xmlProperty,boolean defaults){
        boolean realValue = defaults;
        String tempValue = JiveGlobals.getXMLProperty(xmlProperty);
        if(!StringUtils.isNullOrEmpty(tempValue)){
            realValue = Boolean.parseBoolean(tempValue);
        }
        addProperty(xmlProperty,realValue);
        return realValue;
    }

    private static void addProperty(String xmlProperty,Object value){
        properties.add(new Property(xmlProperty,value));
    }

    public static void print(){
        Iterator<Property> iterator = properties.iterator();
        while(iterator.hasNext()){
            Property property = iterator.next();
            logger.info(property.getName()+" - "+property.getValue());
        }
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property{

        private String name;

        private Object value;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Property property = (Property) o;
            return name.equals(property.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
