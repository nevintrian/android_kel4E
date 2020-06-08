package com.example.inventoria.ui.sales;


import com.example.inventoria.network.response.UserResponse;


public interface SalesView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);

}
