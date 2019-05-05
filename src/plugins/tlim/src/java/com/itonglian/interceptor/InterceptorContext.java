package com.itonglian.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.itonglian.bean.Protocol;
import com.itonglian.exception.ExceptionReply;
import com.itonglian.interceptor.impl.*;
import com.itonglian.utils.MessageUtils;
import com.itonglian.utils.StringUtils;
import org.jivesoftware.openfire.PacketDeliverer;
import org.jivesoftware.openfire.XMPPServer;
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

    private static PacketDeliverer packetDeliverer = XMPPServer.getInstance().getPacketDeliverer();


    public void handler(Message message) throws Exception {

        String jsonStr = message.getBody();

        if(!isValidJson(jsonStr)){
            throw new ExceptionReply("error-000",message,packetDeliverer);
        }

        Protocol protocol = JSONObject.parseObject(jsonStr,Protocol.class);

        if(protocol==null){
            throw new ExceptionReply("error-005",message,packetDeliverer);
        }

        String msgType = protocol.getMsg_type();

        if(!MessageUtils.isValidMsgType(msgType)){
            throw new ExceptionReply("error-001",message,packetDeliverer);
        }

        switch (msgType){
            case "MTT-000":
            case "MTT-001":
            case "MTT-002":
            case "MTT-003":
                interceptor = new ChatNormalInterceptor();
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
            case "MTB-100":
                interceptor = new ChatSystemInterceptor();
                break;
            default:
                if(msgType.contains("MTT")){
                    interceptor = new ChatNormalInterceptor();
                }
                if(msgType.contains("MTS")){
                    interceptor = new SessionNormalInterceptor();
                }
                return;
        }

        interceptor.handler(protocol,message);
    }



    private boolean isValidJson(String jsonStr){
        if(StringUtils.isNullOrEmpty(jsonStr)){
            return false;
        }
        return true;
    }


}
