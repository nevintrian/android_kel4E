package com.example.inventoria.ui.sales.search;


import com.example.inventoria.network.response.UserResponse;

public interface SearchView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);
}
