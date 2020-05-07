package com.example.inventoria.network.response;


import com.example.inventoria.model.User;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse extends BaseResponse {

    @Expose
    @SerializedName("data")
    List<User> data;

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
