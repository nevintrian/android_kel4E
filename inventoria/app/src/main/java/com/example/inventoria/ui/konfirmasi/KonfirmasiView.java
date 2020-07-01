package com.example.inventoria.ui.konfirmasi;


import com.example.inventoria.network.response.KeluarResponse;

public interface KonfirmasiView {

    void showProgress();
    void hideProgress();
    void statusSuccess(KeluarResponse keluarResponse);
    void loadMore(KeluarResponse keluarResponse);
    void statusError(String message);

}
