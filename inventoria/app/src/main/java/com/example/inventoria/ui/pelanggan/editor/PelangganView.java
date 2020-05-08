package com.example.inventoria.ui.pelanggan.editor;

public interface PelangganView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
