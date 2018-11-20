package com.itonglian.utils;

import com.alibaba.fastjson.JSON;
import com.itonglian.entity.OfCustomOffline;
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
        if(StringUtils.isNullOrEmpty(sessionName)){
            sessionName = "";
        }
        if(sessionName.length()>6){
            sessionName = sessionName.substring(0,6)+"...";
        }
        String symbol = "("+sessionName+"):";

        String sysMsg = "收到一条系统消息";
        String sysFileMsg = "发送了一个文件消息";
        String sysImgMsg = "发送了一个图片消息";
        String sysVoiceMsg = "发送了一条语音消息";
        switch (ofMessage.getMsg_type()){
            case "MTT-000":
                content = user.getUser_name()+":"+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                if(content.length()>=30){
                    content = content.substring(0,30)+"...";
                }
                break;
            case "MTS-000":
                content = user.getUser_name()+symbol+JSON.parseArray(ofMessage.getBody(),Body.class).get(0).getText();
                if(content.length()>=30){
                    content = content.substring(0,30)+"...";
                }
                break;
            case "MTT-001":
                content = user.getUser_name()+":"+sysImgMsg;
                break;
            case "MTS-001":
                content = user.getUser_name()+symbol+sysImgMsg;
                break;
            case "MTT-002":
                content = user.getUser_name()+":"+sysFileMsg;
                break;
            case "MTS-002":
                content = user.getUser_name()+symbol+sysFileMsg;
                break;
            case "MTT-003":
                content = user.getUser_name()+":"+sysVoiceMsg;
                break;
            case "MTT-200":
                content = user.getUser_name()+":"+"向您发起清空消息请求";
                break;
            case "MTT-201":
                content = user.getUser_name()+":"+"拒绝清空消息";
                break;
            case "MTS-003":
                content = user.getUser_name()+symbol+sysVoiceMsg;
                break;
            case "MTT-101":
                content = user.getUser_name()+"撤回了一条消息";
                break;
            case "MTS-101":
                content = user.getUser_name()+symbol+"撤回了一条消息";
                break;
            case "MTS-102":
            case "MTS-103":
            case "MTS-104":
            case "MTS-105":
            case "MTS-106":
            case "MTS-107":
                content = sysMsg;
                break;
            case "MTB-000":
                content = "收到了一条审批消息";
                break;
            case "MTB-001":
                content = "收到了一条朋友圈转发消息";
                break;
            default:
                content = "收到了一条新消息";
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

    public static OfCustomOffline toOffline(OfMessage ofMessage){
        if(ofMessage==null){
            return null;
        }
        OfCustomOffline ofCustomOffline = new OfCustomOffline();
        ofCustomOffline.setId_(ofMessage.getId_());
        ofCustomOffline.setMsg_id(ofMessage.getMsg_id());
        ofCustomOffline.setMsg_status(0);
        ofCustomOffline.setBody(ofMessage.getBody());
        ofCustomOffline.setMsg_from(ofMessage.getMsg_from());
        ofCustomOffline.setMsg_to(ofMessage.getMsg_to());
        ofCustomOffline.setMsg_type(ofMessage.getMsg_type());
        ofCustomOffline.setMsg_time(ofMessage.getMsg_time());
        ofCustomOffline.setSession_id(ofMessage.getSession_id());
        return ofCustomOffline;
    }

}
