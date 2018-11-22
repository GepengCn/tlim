package com.itonglian.entity;

public class OfCustomOffline extends OfMessage{

    private int msg_status;

    private String delete_user;

    public String getDelete_user() {
        return delete_user;
    }

    public void setDelete_user(String delete_user) {
        this.delete_user = delete_user;
    }

    public int getMsg_status() {
        return msg_status;
    }

    public void setMsg_status(int msg_status) {
        this.msg_status = msg_status;
    }
}
