package com.example.inventoria.ui.barang;


import com.example.inventoria.network.response.BarangResponse;


public interface BarangView {

    void showProgress();
    void hideProgress();
    void statusSuccess(BarangResponse barangResponse);
    void statusError(String message);

}
