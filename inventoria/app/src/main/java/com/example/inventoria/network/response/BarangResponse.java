package com.example.inventoria.network.response;


import com.example.inventoria.model.Barang;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BarangResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    List<Barang> data;

    public List<Barang> getData() {
        return data;
    }

    public void setData(List<Barang> data) {
        this.data = data;
    }
}
