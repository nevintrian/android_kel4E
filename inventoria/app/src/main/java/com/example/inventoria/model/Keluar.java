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
    @SerializedName("nama")
    String nama;
    @Expose
    @SerializedName("id_barang")
    String id_barang;
    @Expose
    @SerializedName("nama_barang")
    String nama_barang;
    @Expose
    @SerializedName("tgl_keluar")
    String tgl_keluar;
    @Expose
    @SerializedName("qty_keluar")
    String qty_keluar;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) { this.nama = nama; }

    public String getId_barang() { return id_barang; }

    public void setId_barang(String id_barang) { this.id_barang = id_barang; }

    public String getNama_barang() { return nama_barang; }

    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }

    public String getTgl_keluar() { return tgl_keluar; }

    public void setTgl_keluar(String tgl_keluar) { this.tgl_keluar = tgl_keluar; }

    public String getQty_keluar() { return qty_keluar; }

    public void setQty_keluar(String qty_keluar) { this.qty_keluar = qty_keluar; }

    public String getTotal_keluar() { return total_keluar; }

    public void setTotal_keluar(String total_keluar) {
        this.total_keluar = total_keluar;
    }
}
