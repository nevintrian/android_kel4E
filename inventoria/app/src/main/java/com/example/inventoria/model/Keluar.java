package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Keluar {

    @Expose
    @SerializedName("id_keluar")
    String id_keluar;
    @Expose
    @SerializedName("id_user")
    String id_user;
    @Expose
    @SerializedName("tgl_keluar")
    String tgl_keluar;
    @Expose
    @SerializedName("total_keluar")
    String total_keluar;

    public Keluar(String total_keluar) { this.total_keluar = total_keluar; }

    public String getId_keluar() { return id_keluar; }

    public void setId_keluar(String id_keluar) {
        this.id_keluar = id_keluar;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) { this.id_user = id_user; }

    public String getTgl_keluar() {
        return tgl_keluar;
    }

    public void setTgl_keluar(String tgl_keluar) { this.tgl_keluar = tgl_keluar; }

    public String getTotal_keluar() { return total_keluar; }

    public void setTotal_keluar(String total_keluar) {
        this.total_keluar = total_keluar;
    }
}
