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
    @SerializedName("nama_supplier")
    String nama_supplier;
    @Expose
    @SerializedName("id_barang")
    String id_barang;
    @Expose
    @SerializedName("nama_barang")
    String nama_barang;
    @Expose
    @SerializedName("tgl_masuk")
    String tgl_masuk;
    @Expose
    @SerializedName("qty_masuk")
    String qty_masuk;
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

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) { this.nama_supplier = nama_supplier; }

    public String getId_barang() { return id_barang; }

    public void setId_barang(String id_barang) { this.id_barang = id_barang; }

    public String getNama_barang() { return nama_barang; }

    public void setNama_barang(String nama_barang) { this.nama_barang = nama_barang; }

    public String getTgl_masuk() { return tgl_masuk; }

    public void setTgl_masuk(String tgl_masuk) { this.tgl_masuk = tgl_masuk; }

    public String getQty_masuk() { return qty_masuk; }

    public void setQty_masuk(String qty_masuk) { this.qty_masuk = qty_masuk; }

    public String getTotal_masuk() { return total_masuk; }

    public void setTotal_masuk(String total_masuk) {
        this.total_masuk = total_masuk;
    }
}
