package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {

    @Expose
    @SerializedName("id_supplier")
    String id_supplier;
    @Expose
    @SerializedName("nama_supplier")
    String nama_supplier;
    @Expose
    @SerializedName("no_telp")
    String no_telp;
    @Expose
    @SerializedName("alamat")
    String alamat;

    public Supplier(String alamat) { this.alamat = alamat; }

    public String getId_supplier() { return id_supplier; }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) { this.nama_supplier = nama_supplier; }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_hp(String no_telp) { this.no_telp = no_telp; }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
