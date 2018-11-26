package com.itonglian.entity;

import org.jivesoftware.util.JiveConstants;

public class OfStyle {

    private String style_id;

    private String style_name;

    private int style_value;

    private String user_id;

    public OfStyle(){
    }

    public OfStyle(String style_name, int style_value, String user_id) {
        this.style_name = style_name;
        this.style_value = style_value;
        this.user_id = user_id;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
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
