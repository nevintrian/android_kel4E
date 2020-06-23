package com.example.inventoria.ui.masuk.search;


import com.example.inventoria.network.response.MasukResponse;

public interface SearchView {

    void showProgress();
    void hideProgress();
    void statusSuccess(MasukResponse masukResponse);
    void statusError(String message);
}
