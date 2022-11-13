package com.example.appbanhang.model;

public class Name_Users {
    public String id;
    public String userName;

    public Name_Users() {
    }

    public Name_Users(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
