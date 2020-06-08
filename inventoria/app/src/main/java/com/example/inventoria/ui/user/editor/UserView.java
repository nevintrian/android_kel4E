package com.example.inventoria.ui.user.editor;

public interface UserView {

    void statusSuccess(String message);
    void statusError(String message);
    void showProgress();
    void hideProgress();

}
