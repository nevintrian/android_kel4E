package com.example.inventoria.ui.user.editor;

public interface UserView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
}
