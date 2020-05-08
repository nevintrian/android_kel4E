package com.example.inventoria.ui.sales.editor;

public interface SalesView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
