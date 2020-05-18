package com.example.inventoria.ui.home.editor;

public interface HomeView {

    void statusSuccess(String message);
    void statusError(String message);
    void showProgress();
    void hideProgress();

}
