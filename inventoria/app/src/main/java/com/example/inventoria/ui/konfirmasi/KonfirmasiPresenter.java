package com.example.inventoria.ui.konfirmasi;

import android.util.Log;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.KeluarResponse;
import com.example.inventoria.ui.keluar.KeluarView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KonfirmasiPresenter {

    KonfirmasiView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public KonfirmasiPresenter(KonfirmasiView keluarView) {
        this.view = keluarView;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getKeluars() {
        view.showProgress();
        disposable.add(
                apiInterface.getKonfirmasis()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KeluarResponse>(){
                            @Override
                            public void onNext(KeluarResponse keluarResponse) {
                                if (keluarResponse.getStatus().equals("true")) {
                                    view.statusSuccess(keluarResponse);
                                } else {
                                    view.statusError(keluarResponse.getStatus());
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
