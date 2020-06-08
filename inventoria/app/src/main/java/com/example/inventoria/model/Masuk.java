package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Masuk {

    @Expose
    @SerializedName("id_masuk")
    String id_masuk;
    @Expose
    @SerializedName("id_supplier")
    String id_supplier;
    @Expose
    @SerializedName("tgl_masuk")
    String tgl_masuk;
    @Expose
    @SerializedName("total_masuk")
    String total_masuk;

    public Masuk(String total_masuk) { this.total_masuk = total_masuk; }

    public String getId_masuk() { return id_masuk; }

    public void setId_masuk(String id_masuk) {
        this.id_masuk = id_masuk;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) { this.id_supplier = id_supplier; }

    public String getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) { this.tgl_masuk = tgl_masuk; }

    public String getTotal_masuk() { return total_masuk; }

    public void setTotal_masuk(String total_masuk) {
        this.total_masuk = total_masuk;
    }
}
