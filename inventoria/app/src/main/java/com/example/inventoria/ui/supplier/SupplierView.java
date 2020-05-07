package com.example.inventoria.ui.supplier;


import com.example.inventoria.network.response.SupplierResponse;

public interface SupplierView {

    void showProgress();
    void hideProgress();
    void statusSuccess(SupplierResponse supplierResponse);
    void loadMore(SupplierResponse supplierResponse);
    void statusError(String message);

}
