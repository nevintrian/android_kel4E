package com.example.inventoria.ui.keluar.editor;

import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.network.response.UserResponse;

public interface KeluarView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
    void setListUser(UserResponse userResponse);
    void setListBarang(BarangResponse barangResponse);
}
