package com.example.pattimura.sundawenang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by wildan on 22/03/17.
 */

public class LowonganModel implements Parcelable {
    public static final Creator<LowonganModel> CREATOR = new Creator<LowonganModel>() {
        @Override
        public LowonganModel createFromParcel(Parcel in) {
            return new LowonganModel(in);
        }

        @Override
        public LowonganModel[] newArray(int size) {
            return new LowonganModel[size];
        }
    };
    private String judul, tanggal, deskripsi;
    private ArrayList<GambarProduk> daftargambar;

    public LowonganModel(String judul, String tanggal, String deskripsi) {
        this.judul = judul;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
        daftargambar = new ArrayList<>();
    }

    protected LowonganModel(Parcel in) {
        judul = in.readString();
        tanggal = in.readString();
        deskripsi = in.readString();
        daftargambar = in.readArrayList(GambarProduk.class.getClassLoader());
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public ArrayList<GambarProduk> getDaftargambar() {
        return daftargambar;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(tanggal);
        dest.writeString(deskripsi);
        dest.writeList(daftargambar);
    }
}
