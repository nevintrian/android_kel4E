package com.example.inventoria.ui.gudang.editor;

public interface GudangView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
