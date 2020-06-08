package com.example.inventoria.ui.keluar;


import com.example.inventoria.network.response.KeluarResponse;

public interface KeluarView {

    void showProgress();
    void hideProgress();
    void statusSuccess(KeluarResponse keluarResponse);
    void loadMore(KeluarResponse keluarResponse);
    void statusError(String message);

}
