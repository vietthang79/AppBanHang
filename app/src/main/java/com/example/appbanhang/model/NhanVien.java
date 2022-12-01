package com.example.appbanhang.model;

import java.sql.Date;

public class NhanVien {
    public String id;
    public String NameNV;
    public String maNV;
    public String luongNV;
    public String calam;
    public String Quyen_Truy_Cap;
    public String TrangThai;

    public NhanVien() {
    }

    public NhanVien(String id, String nameNV, String maNV, String luongNV, String calam, String quyen_Truy_Cap, String trangThai) {
        this.id = id;
        NameNV = nameNV;
        this.maNV = maNV;
        this.luongNV = luongNV;
        this.calam = calam;
        Quyen_Truy_Cap = quyen_Truy_Cap;
        TrangThai = trangThai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameNV() {
        return NameNV;
    }

    public void setNameNV(String nameNV) {
        NameNV = nameNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getLuongNV() {
        return luongNV;
    }

    public void setLuongNV(String luongNV) {
        this.luongNV = luongNV;
    }

    public String getCalam() {
        return calam;
    }

    public void setCalam(String calam) {
        this.calam = calam;
    }

    public String getQuyen_Truy_Cap() {
        return Quyen_Truy_Cap;
    }

    public void setQuyen_Truy_Cap(String quyen_Truy_Cap) {
        Quyen_Truy_Cap = quyen_Truy_Cap;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
