package com.example.inventoria.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("id_user")
    String id_user;
    @Expose
    @SerializedName("email")
    String email;
    @Expose
    @SerializedName("username")
    String username;
    @Expose
    @SerializedName("password")
    String password;
    @Expose
    @SerializedName("level")
    String level;
    @Expose
    @SerializedName("nama")
    String nama;
    @Expose
    @SerializedName("tgl_lahir")
    String tgl_lahir;
    @Expose
    @SerializedName("jenis_kelamin")
    String jenis_kelamin;
    @Expose
    @SerializedName("no_telp")
    String no_telp;
    @Expose
    @SerializedName("alamat")
    String alamat;
    @Expose
    @SerializedName("foto")
    String foto;



    public String getId_user() { return id_user; }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username =username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getLevel() { return level; }

    public void setLevel(String level) { this.level = level; }

    public String getNama() { return nama; }

    public void setNama(String nama) { this.nama = nama; }

    public String getTgl_lahir() { return tgl_lahir; }

    public void setTgl_lahir(String tgl_lahir) { this.tgl_lahir = tgl_lahir; }

    public String getJenis_kelamin() { return jenis_kelamin; }

    public void setJenis_kelamin(String jenis_kelamin) { this.jenis_kelamin = jenis_kelamin; }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) { this.no_telp = no_telp; }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
