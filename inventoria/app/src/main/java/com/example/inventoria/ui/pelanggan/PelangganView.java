package com.example.inventoria.ui.pelanggan;


import com.example.inventoria.network.response.UserResponse;

public interface PelangganView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);

}
