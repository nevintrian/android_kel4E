package com.example.inventoria.ui.barang.search;


import com.example.inventoria.network.response.BarangResponse;

public interface SearchView {

    void showProgress();
    void hideProgress();
    void statusSuccess(BarangResponse barangResponse);
    void statusError(String message);
}
