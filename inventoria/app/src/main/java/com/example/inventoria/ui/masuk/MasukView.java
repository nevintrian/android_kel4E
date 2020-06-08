package com.example.inventoria.ui.masuk;


import com.example.inventoria.network.response.MasukResponse;

public interface MasukView {

    void showProgress();
    void hideProgress();
    void statusSuccess(MasukResponse masukResponse);
    void loadMore(MasukResponse masukResponse);
    void statusError(String message);

}
