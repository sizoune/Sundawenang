package com.example.pattimura.sundawenang.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by wildan on 20/03/17.
 */

public class BeritaModel implements Parcelable {

    public static final Creator<BeritaModel> CREATOR = new Creator<BeritaModel>() {
        @Override
        public BeritaModel createFromParcel(Parcel in) {
            return new BeritaModel(in);
        }

        @Override
        public BeritaModel[] newArray(int size) {
            return new BeritaModel[size];
        }
    };

    private String isiBerita, judul, tanggal, kategori, urlvideo;
    private ArrayList<GambarProduk> daftargambar;

    public BeritaModel(String isiBerita, String judul, String tanggal, String kategori) {
        this.isiBerita = isiBerita;
        this.judul = judul;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.urlvideo = "";
        daftargambar = new ArrayList<>();
    }

    protected BeritaModel(Parcel in) {
        isiBerita = in.readString();
        judul = in.readString();
        tanggal = in.readString();
        kategori = in.readString();
        urlvideo = in.readString();
        daftargambar = in.readArrayList(GambarProduk.class.getClassLoader());
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
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

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getUrlvideo() {
        return urlvideo;
    }

    public void setUrlvideo(String urlvideo) {
        this.urlvideo = urlvideo;
    }

    public ArrayList<GambarProduk> getDaftargambar() {
        return daftargambar;
    }

    public void setDaftargambar(ArrayList<GambarProduk> daftargambar) {
        this.daftargambar = daftargambar;
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
        dest.writeString(isiBerita);
        dest.writeString(judul);
        dest.writeString(tanggal);
        dest.writeString(kategori);
        dest.writeString(urlvideo);
        dest.writeList(daftargambar);
    }
}
