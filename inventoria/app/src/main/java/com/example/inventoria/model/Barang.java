package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Barang {

    @Expose
    @SerializedName("id_barang")
    String id_barang;
    @Expose
    @SerializedName("id_supplier")
    String id_supplier;
    @Expose
    @SerializedName("nama_barang")
    String nama_barang;
    @Expose
    @SerializedName("kemasan")
    String kemasan;
    @Expose
    @SerializedName("merk")
    String merk;
    @Expose
    @SerializedName("jenis")
    String jenis;
    @Expose
    @SerializedName("stok")
    String stok;
    @Expose
    @SerializedName("harga")
    String harga;
    @Expose
    @SerializedName("terjual")
    String terjual;
    @Expose
    @SerializedName("foto_barang")
    String foto_barang;

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getKemasan() { return kemasan; }

    public void setKemasan(String kemasan) { this.kemasan = kemasan; }

    public String getMerk() { return merk; }

    public void setMerk(String merk) { this.merk = merk; }

    public String getJenis() { return jenis; }

    public void setJenis(String jenis) { this.jenis = jenis; }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTerjual() {
        return terjual;
    }

    public void setTerjual(String terjual) {
        this.terjual = terjual;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }
}
