package com.example.inventoria.ui.masuk;

import android.util.Log;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.MasukResponse;
import com.example.inventoria.ui.masuk.MasukView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MasukPresenter {

    com.example.inventoria.ui.masuk.MasukView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public MasukPresenter(MasukView masukView) {
        this.view = masukView;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getMasuks() {
        view.showProgress();
        disposable.add(
                apiInterface.getMasuks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MasukResponse>(){
                            @Override
                            public void onNext(MasukResponse masukResponse) {
                                if (masukResponse.getStatus().equals("true")) {
                                    view.statusSuccess(masukResponse);
                                } else {
                                    view.statusError(masukResponse.getStatus());
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
