package com.itonglian.entity;

import org.jivesoftware.util.JiveConstants;

public class OfPubact {

    private long id_;

    private String title;

    private String content;

    private String user_id;

    private String ts;

    private String session_id;


    public class ID_Contants extends JiveConstants {
        public static final int PRIMARY_KEY =55;
    }


    public long getId_() {
        return id_;
    }

    public void setId_(long id_) {
        this.id_ = id_;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
