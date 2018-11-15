package com.itonglian.utils;

import com.alibaba.fastjson.JSON;
import com.itonglian.entity.OfMessage;
import com.itonglian.entity.User;

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


    public static String messageContext(OfMessage ofMessage,String sessionName){
        String content;
        User user = UserCacheManager.findUserByKey(ofMessage.getMsg_from());
        sessionName = "";
        String symbol = "[群聊]";
        switch (ofMessage.getMsg_type()){
            case "MTT-000":
                content = user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTS-000":
                content = sessionName+symbol+user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTT-001":
                content = user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTS-001":
                content = sessionName+symbol+user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTT-002":
                content = user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTS-002":
                content = sessionName+symbol+user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTT-003":
                content = user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTS-003":
                content = sessionName+symbol+user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                break;
            case "MTT-101":
            case "MTS-101":
                content = user.getUser_name()+"撤回一条消息";
                break;
            case "MTS-102":
                content = user.getUser_name()+"修改了群名称";
                break;
            case "MTB-000":
                content = "收到一条审批消息";
                break;
            case "MTB-001":
                content = "收到一条朋友圈转发消息";
                break;
            default:
                content = "";
                break;
        }
        return content;
    }

    private static class Body{
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }


    public static String combineKv(String key,String value){
        return key+"(tlimkv)"+value;
    }
}
