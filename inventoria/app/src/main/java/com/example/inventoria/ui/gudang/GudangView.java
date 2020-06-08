package com.example.inventoria.ui.gudang;


import com.example.inventoria.network.response.UserResponse;

public interface GudangView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);

}
