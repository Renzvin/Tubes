package com.tubespbp.DAO;

import java.util.Date;

public class borrowDAO {
    String username,judul;
    String tanggal_kembali;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public borrowDAO(String username, String judul, String tanggal_kembali) {
        this.username = username;
        this.judul = judul;
        this.tanggal_kembali = tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }
}
