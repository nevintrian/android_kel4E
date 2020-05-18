package com.example.inventoria.ui.home;


import com.example.inventoria.network.response.BarangResponse;


public interface HomeView {

    void showProgress();
    void hideProgress();
    void statusSuccess(BarangResponse barangResponse);
    void statusError(String message);

}
