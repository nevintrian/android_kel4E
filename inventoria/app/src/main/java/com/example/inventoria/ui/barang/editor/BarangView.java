package com.example.inventoria.ui.barang.editor;

public interface BarangView {

    void statusSuccess(String message);
    void statusError(String message);
    void showProgress();
    void hideProgress();

}
