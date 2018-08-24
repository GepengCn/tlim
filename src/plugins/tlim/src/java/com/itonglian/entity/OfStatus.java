package com.itonglian.entity;

import org.jivesoftware.util.JiveConstants;

public class OfStatus {

    private long id_;

    private String msg_id;

    private String msg_to;

    private String msg_type;

    private int status;

    private String session_id;

    public class ID_Contants extends JiveConstants {
        public static final int STATUS_KEY =53;
    }

    public long getId_() {
        return id_;
    }

    public void setId_(long id_) {
        this.id_ = id_;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_to() {
        return msg_to;
    }

    public void setMsg_to(String msg_to) {
        this.msg_to = msg_to;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
