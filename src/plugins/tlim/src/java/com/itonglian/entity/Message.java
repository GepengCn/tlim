package com.itonglian.entity;

public class Message {

    /** 消息id;唯一;uuid */
    private String msg_id;
    /** 消息类型,eg.MTT-000 */
    private String msg_type;
    /** 消息发送者;userId */
    private String msg_from;
    /** 消息接收者;userId */
    private String msg_to;
    /** 时间戳 */
    private String msg_time;
    /** 消息体 */
    private String body;

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getMsg_from() {
        return msg_from;
    }

    public void setMsg_from(String msg_from) {
        this.msg_from = msg_from;
    }

    public String getMsg_to() {
        return msg_to;
    }

    public void setMsg_to(String msg_to) {
        this.msg_to = msg_to;
    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
