package com.itonglian.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.impl.*;
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

        switch (msgType){
            case "MTT-000":
            case "MTT-001":
            case "MTT-002":
            case "MTT-003":
                interceptor = new ChatInterceptor();
                break;
            case "MTS-000":
            case "MTS-001":
            case "MTS-002":
            case "MTS-003":
                interceptor = new SessionNormalInterceptor();
                break;
            case "MTT-100":
                interceptor = new ChatReadInterceptor();
                break;
            case "MTT-101":
                interceptor = new ChatRevokeInterceptor();
                break;
            case "MTT-200":
            case "MTT-201":
                interceptor = new ChatClearInterceptor();
                break;
            case "MTS-100":
                interceptor = new SessionReadInterceptor();
                break;
            case "MTS-101":
                interceptor = new SessionRevokeInterceptor();
                break;
            case "MTS-102":
                interceptor = new SessionRenameInterceptor();
                break;
            case "MTS-103":
                interceptor = new SessionRmvSubInterceptor();
                break;
            case "MTS-104":
                interceptor = new SessionExitInterceptor();
                break;
            case "MTS-105":
                interceptor = new SessionCreatedInterceptor();
                break;
            case "MTS-106":
                interceptor = new SessionInvitedInterceptor();
                break;
            case "MTS-107":
                interceptor = new SessionDissolvedInterceptor();
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
