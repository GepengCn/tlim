package com.itonglian.entity;

import org.jivesoftware.util.JiveConstants;

public class OfStyle {

    private long style_id;

    private String style_name;

    private int style_value;

    private String user_id;

    public class ID_Contants extends JiveConstants {
        public static final int STYLE_KEY =58;
    }


    public long getStyle_id() {
        return style_id;
    }

    public void setStyle_id(long style_id) {
        this.style_id = style_id;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public int getStyle_value() {
        return style_value;
    }

    public void setStyle_value(int style_value) {
        this.style_value = style_value;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
