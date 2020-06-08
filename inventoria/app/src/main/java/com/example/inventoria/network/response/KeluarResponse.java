package com.example.inventoria.network.response;


import com.example.inventoria.model.Keluar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KeluarResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    List<Keluar> data;

    public List<Keluar> getData() {
        return data;
    }

    public void setData(List<Keluar> data) {
        this.data = data;
    }
}
