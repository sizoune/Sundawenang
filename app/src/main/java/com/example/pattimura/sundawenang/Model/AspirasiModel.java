package com.example.pattimura.sundawenang.Model;

import java.util.Date;

/**
 * Created by wildan on 18/03/17.
 */

public class AspirasiModel {
    private String isi,nama,urlKTP,rt,rw;


    public AspirasiModel(String isi, String nama, String urlKTP, String rt, String rw) {
        this.isi = isi;
        this.nama = nama;
        this.urlKTP = urlKTP;
        this.rt = rt;
        this.rw = rw;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrlKTP() {
        return urlKTP;
    }

    public void setUrlKTP(String urlKTP) {
        this.urlKTP = urlKTP;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }
}
