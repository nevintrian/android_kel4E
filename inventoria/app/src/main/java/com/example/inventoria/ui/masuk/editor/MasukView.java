package com.example.inventoria.ui.masuk.editor;

public interface MasukView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
