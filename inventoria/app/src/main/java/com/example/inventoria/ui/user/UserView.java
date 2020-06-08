package com.example.inventoria.ui.user;


import com.example.inventoria.network.response.UserResponse;


public interface UserView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);

}
