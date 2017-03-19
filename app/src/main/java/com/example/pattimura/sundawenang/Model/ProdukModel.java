package com.example.pattimura.sundawenang.Model;

import java.util.ArrayList;

/**
 * Created by wildan on 19/03/17.
 */

public class ProdukModel {
    private String deskripsi, nama, tanggal;
    private ArrayList<GambarProduk> daftargambar;

    public ProdukModel(String deskripsi, String nama, String tanggal) {
        this.deskripsi = deskripsi;
        this.nama = nama;
        this.tanggal = tanggal;
        daftargambar = new ArrayList<>();
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void addGambar(String nama, int url) {
        daftargambar.add(new GambarProduk(nama, url));
    }

    public GambarProduk getGambarke(int i) {
        return daftargambar.get(i);
    }

    public int getBanyakgambar() {
        return daftargambar.size();
    }

    public boolean cekDaftarGambar() {
        return daftargambar.isEmpty();
    }
}
