package com.itonglian.utils;

import com.itonglian.enums.DBType;
import org.apache.ibatis.session.SqlSessionFactory;

public class DBUtils {

    public static DBType getDBType(){
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getInstance().createSessionFactory();
        switch (sqlSessionFactory.getConfiguration().getDatabaseId()){
            case "Oracle":
                return DBType.Oracle;
            case "SQLServer":
                return DBType.SQLServer;
            default:
                return DBType.MySQL;
        }

    }
}
