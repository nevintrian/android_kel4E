package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("id_user")
    String id_user;
    @Expose
    @SerializedName("nama")
    String nama;
    @Expose
    @SerializedName("no_telp")
    String no_telp;
    @Expose
    @SerializedName("alamat")
    String alamat;

    public User(String alamat) { this.alamat = alamat; }

    public String getId_user() { return id_user; }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) { this.no_telp = no_telp; }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
