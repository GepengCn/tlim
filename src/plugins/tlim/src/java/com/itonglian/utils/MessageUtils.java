package com.itonglian.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

public class MessageUtils {

    private static String DOMAIN = "@im.itonglian.com";

    public static boolean isValidMsgType(String msgType){
        if(StringUtils.isNullOrEmpty(msgType)){
            return false;
        }
        if(!msgType.contains("-")){
            return false;
        }

        if(msgType.contains("MTT")||msgType.contains("MTC")||msgType.contains("MTS")||msgType.contains("MTB")){
            return true;
        }
        return false;
    }

    public static String toJid(String userId){
        return userId+DOMAIN;
    }


    public static String getTs(){
        return new Date().getTime()+"";
    }


    public static void setResponse(HttpServletResponse resp){
        resp.setCharacterEncoding("utf-8");

        resp.setContentType("application/json;charset=utf-8");

        resp.setHeader("Access-Control-Allow-Origin", "*");
    }

    public static String encode(String value){
        try {
            return URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decode(String value){
        try {
            return URLDecoder.decode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
