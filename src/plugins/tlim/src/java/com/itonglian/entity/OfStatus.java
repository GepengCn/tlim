package com.itonglian.entity;

import org.jivesoftware.util.JiveConstants;

public class OfStatus {

    private long id_;

    private String msg_id;

    private String reader;

    private int status;

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

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
