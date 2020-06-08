package com.example.inventoria.ui.keluar.editor;

public interface KeluarView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
