package com.example.inventoria.ui.barang.editor;

import com.example.inventoria.network.response.SupplierResponse;

public interface BarangView {

    void statusSuccess(String message);
    void statusError(String message);
    void showProgress();
    void hideProgress();
    void setListSupplier(SupplierResponse supplierResponse);
}
