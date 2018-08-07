package com.itonglian.entity;

public class User {

    private String userId;

    private String userName;

    private String acctLogin;

    private String userEmail;

    private String picUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAcctLogin() {
        return acctLogin;
    }

    public void setAcctLogin(String acctLogin) {
        this.acctLogin = acctLogin;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
