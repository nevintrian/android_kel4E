package com.example.inventoria.ui.keluar.search;


import com.example.inventoria.network.response.KeluarResponse;

public interface SearchView {

    void showProgress();
    void hideProgress();
    void statusSuccess(KeluarResponse keluarResponse);
    void statusError(String message);
}
