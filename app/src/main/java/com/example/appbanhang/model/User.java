package com.example.appbanhang.model;


public class User {
    public String id;
    public String userName;
    public String email;
    public String password;
//    public String Newpassword;
    public String ngaysinh;
    public String gioitinh;

    public User() {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
//        this.Newpassword = Newpassword;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
    }

    public User(String id, String userName, String email, String password, String Newpassword, String ngaysinh, String gioitinh) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
//        this.Newpassword = Newpassword;
        this.ngaysinh = ngaysinh;
        this.gioitinh = gioitinh;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

//    public String getNewpassword() {
//        return Newpassword;
//    }
//
//    public void setNewpassword(String newpassword) {
//        Newpassword = newpassword;
//    }
}

