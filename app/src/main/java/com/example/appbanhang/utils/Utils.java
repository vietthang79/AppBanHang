package com.example.appbanhang.utils;

import com.example.appbanhang.model.Dangki;
import com.example.appbanhang.model.GioHang;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL ="http://10.22.218.45/banhang/";

    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    private static Dangki dangki_current = new Dangki();
}
