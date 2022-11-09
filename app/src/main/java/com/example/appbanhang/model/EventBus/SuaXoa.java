package com.example.appbanhang.model.EventBus;

import com.example.appbanhang.model.SanPhamMoi;

public class SuaXoa {
    SanPhamMoi sanPhamMoi;

    public SuaXoa(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public SanPhamMoi getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(SanPhamMoi sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}
