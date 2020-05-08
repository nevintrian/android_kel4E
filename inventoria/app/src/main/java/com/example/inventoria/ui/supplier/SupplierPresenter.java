package com.example.inventoria.ui.supplier;

import android.util.Log;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.SupplierResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SupplierPresenter {

    SupplierView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SupplierPresenter(SupplierView supplierView) {
        this.view = supplierView;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getSuppliers() {
        view.showProgress();
        disposable.add(
                apiInterface.getSuppliers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SupplierResponse>(){
                            @Override
                            public void onNext(SupplierResponse supplierResponse) {
                                if (supplierResponse.getStatus().equals("true")) {
                                    view.statusSuccess(supplierResponse);
                                } else {
                                    view.statusError(supplierResponse.getStatus());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();

                            }
                        })
        );
    }


    public void detachView() {
        disposable.dispose();
    }

}
