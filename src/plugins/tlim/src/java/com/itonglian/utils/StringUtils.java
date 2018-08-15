package com.itonglian.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String value){
        if(value == null || "".equals(value)){
            return true;
        }
        return false;
    }

    public static int stringToInt(String value){
        if(isNullOrEmpty(value)){
            return 0;
        }
        return Integer.parseInt(value);
    }

    public static int StringToMaxInt(String value){
        if(isNullOrEmpty(value)){
            return 0;
        }
        if("max".equalsIgnoreCase(value)){
            return Integer.MAX_VALUE;
        }
        return Integer.parseInt(value);
    }
}
