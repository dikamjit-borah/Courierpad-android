package com.courierpad.in.models;

public class UserModel {
    public UserModel(String user_type, int user_id, String user_password) {
        this.user_type = user_type;
        this.user_id = user_id;
        this.user_password = user_password;
    }

    public UserModel(String status, String data) {
        this.data = data;
        this.status = status;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String user_type;
    private int user_id;
    private String user_password;
    private String data;
    private String status;
}
