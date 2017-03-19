package com.example.pattimura.sundawenang.Model;

import java.util.ArrayList;

/**
 * Created by wildan on 19/03/17.
 */

public class GambarProduk {
    private String nama;
    private int url;

    public GambarProduk(String nama, int url) {
        this.nama = nama;
        this.url = url;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
