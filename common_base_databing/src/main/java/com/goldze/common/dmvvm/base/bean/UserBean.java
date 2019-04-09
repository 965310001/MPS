package com.goldze.common.dmvvm.base.bean;

public class UserBean {

    private String username;
    private String userid;
    private String key;
    private String error;

    public String getError() {
        return error == null ? "" : error;
    }

    public void setError(String error) {
        this.error = error == null ? "" : error;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username == null ? "" : username;
    }

    public String getUserid() {
        return userid == null ? "" : userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? "" : userid;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }
    
}
