package com.example.inventoria.ui.profil.editor;

public interface ProfilView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
