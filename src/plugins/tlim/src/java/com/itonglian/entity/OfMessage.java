package com.itonglian.entity;
/**
 * <p> 概述：消息实体
 * <p> 功能：消息实体
 * <p> 作者：葛鹏
 * <p> 创建时间：2018/8/2 14:23
 * <p> 类调用特殊情况：
 */
public class OfMessage {
    /** 消息id;唯一;uuid */
    private String msgId;
    /** 消息类型,eg.MTT-000 */
    private String msgType;
    /** 消息发送者;userId */
    private String msgFrom;
    /** 消息接收者;userId */
    private String msgTo;
    /** 时间戳 */
    private String ts;
    /** 消息体 */
    private String body;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgFrom() {
        return msgFrom;
    }

    public void setMsgFrom(String msgFrom) {
        this.msgFrom = msgFrom;
    }

    public String getMsgTo() {
        return msgTo;
    }

    public void setMsgTo(String msgTo) {
        this.msgTo = msgTo;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
