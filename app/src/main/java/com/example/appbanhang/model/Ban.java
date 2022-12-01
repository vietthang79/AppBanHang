package com.example.appbanhang.model;

public class Ban {
    private String tenBan;
    private int id;

    public Ban() {
    }

    public Ban(String tenBan, int id) {
        this.tenBan = tenBan;
        this.id = id;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
