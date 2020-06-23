package com.example.inventoria.ui.supplier.search;


import com.example.inventoria.network.response.SupplierResponse;

public interface SearchView {

    void showProgress();
    void hideProgress();
    void statusSuccess(SupplierResponse supplierResponse);
    void statusError(String message);
}
