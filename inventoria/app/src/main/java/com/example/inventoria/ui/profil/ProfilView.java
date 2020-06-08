package com.example.inventoria.ui.profil;


import com.example.inventoria.network.response.UserResponse;

public interface ProfilView {

    void showProgress();
    void hideProgress();
    void statusSuccess(UserResponse userResponse);
    void statusError(String message);

}
