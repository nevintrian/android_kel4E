package com.example.inventoria.ui.masuk.editor;

import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.SupplierResponse;

public interface MasukView {

    void showProgress();
    void hideProgress();
    void statusSuccess(String message);
    void statusError(String message);
    void setListSupplier(SupplierResponse supplierResponse);
    void setListBarang(BarangResponse barangResponse);
}
