package com.itonglian.entity;

public class User {

    private String user_id;

    private String user_name;

    private String acct_login;

    private String user_email;

    private String pic_url;

    private String app_push_code;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAcct_login() {
        return acct_login;
    }

    public void setAcct_login(String acct_login) {
        this.acct_login = acct_login;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getApp_push_code() {
        return app_push_code;
    }

    public void setApp_push_code(String app_push_code) {
        this.app_push_code = app_push_code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user= (User) obj;
            return user_id.equals(user.getUser_id());
        }
        return false;
    }
}
