package com.example.pattimura.sundawenang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by wildan on 19/03/17.
 */

public class ProdukModel implements Parcelable {

    public static final Creator<ProdukModel> CREATOR = new Creator<ProdukModel>() {
        @Override
        public ProdukModel createFromParcel(Parcel in) {
            return new ProdukModel(in);
        }

        @Override
        public ProdukModel[] newArray(int size) {
            return new ProdukModel[size];
        }
    };
    private String deskripsi, nama, tanggal;
    private ArrayList<GambarProduk> daftargambar;

    public ProdukModel(String deskripsi, String nama, String tanggal) {
        this.deskripsi = deskripsi;
        this.nama = nama;
        this.tanggal = tanggal;
        daftargambar = new ArrayList<>();
    }

    protected ProdukModel(Parcel in) {
        deskripsi = in.readString();
        nama = in.readString();
        tanggal = in.readString();
        daftargambar = in.readArrayList(GambarProduk.class.getClassLoader());
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

    public ArrayList<GambarProduk> getDaftargambar() {
        return daftargambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deskripsi);
        dest.writeString(nama);
        dest.writeString(tanggal);
        dest.writeList(daftargambar);
    }


}
