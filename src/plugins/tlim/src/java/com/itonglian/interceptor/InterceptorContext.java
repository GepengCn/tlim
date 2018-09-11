package com.itonglian.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.impl.ChatInterceptor;
import com.itonglian.interceptor.impl.SessionInterceptor;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;

/**
 * <p> 概述：拦截器处理中心
 * <p> 功能：拦截器处理中心
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class InterceptorContext {


    private Interceptor interceptor;

    public void handler(Packet packet, Session session) throws Exception {

        if(!(packet instanceof Message)){
            return;
        }

        if(!isValidPacket(packet)){
            return;
        }
        Message message = (Message)packet;

        String jsonStr = message.getBody();



        if(!isValidJson(jsonStr)){
            throw new ExceptionReply("error-000",packet,session);
        }

        Protocol protocol = JSONObject.parseObject(jsonStr,Protocol.class);

        if(protocol==null){
            throw new ExceptionReply("error-005",packet,session);
        }

        String msgType = protocol.getMsg_type();

        if(!MessageUtils.isValidMsgType(msgType)){
            throw new ExceptionReply("error-001",packet,session);
        }

        String preMsgType = msgType.split("-")[0];

        String sufMsgType = msgType.split("-")[1];

        String fst = sufMsgType.substring(0,1);

        switch (preMsgType){
            case "MTT":
                interceptor = new ChatInterceptor();
                break;

            case "MTS":
                interceptor = new SessionInterceptor();
                break;
            default:
                return;
        }

        interceptor.handler(protocol,message);
    }

    private boolean isValidPacket(Packet packet){
        if(packet.getFrom() ==null || packet.getTo() ==null){
            return false;
        }
        /*PacketExtension packetExtension = packet.getExtension("tlim","im.itonglian.com");
        if(packetExtension==null){
            return false;
        }*/
        return true;
    }

    private boolean isValidJson(String jsonStr){
        if(StringUtils.isNullOrEmpty(jsonStr)){
            return false;
        }
        return true;
    }


}
