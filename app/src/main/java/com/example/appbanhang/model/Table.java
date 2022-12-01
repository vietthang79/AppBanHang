package com.example.appbanhang.model;

public class Table {

    private String tenBan;
    private int id;

    public Table() {
    }

    public Table(String tenBan, int id) {
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
