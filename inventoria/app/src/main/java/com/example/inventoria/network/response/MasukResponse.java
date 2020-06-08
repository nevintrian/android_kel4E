package com.example.inventoria.network.response;


import com.example.inventoria.model.Masuk;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MasukResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    List<Masuk> data;

    public List<Masuk> getData() {
        return data;
    }

    public void setData(List<Masuk> data) {
        this.data = data;
    }
}
