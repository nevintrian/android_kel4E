package com.example.inventoria.ui.sales;

import android.util.Log;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.ui.user.UserView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SalesPresenter {

    SalesView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SalesPresenter(SalesView salesView) {
        this.view = salesView;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getSaless() {
        view.showProgress();
        disposable.add(
                apiInterface.getSaless()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>(){
                            @Override
                            public void onNext(UserResponse userResponse) {
                                if (userResponse.getStatus().equals("true")) {
                                    view.statusSuccess(userResponse);
                                } else {
                                    view.statusError(userResponse.getStatus());
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
