package com.example.inventoria.ui.user;

import android.util.Log;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter {

    com.example.inventoria.ui.user.UserView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public UserPresenter(UserView userView) {
        this.view = userView;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getUsers() {
        view.showProgress();
        disposable.add(
                apiInterface.getUsers("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>(){
                            @Override
                            public void onNext(UserResponse userResponse) {
                                if (userResponse.getStatus().equals("success")) {
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

    public void loadMore(String page) {
        disposable.add(
                apiInterface.getUsers(page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<UserResponse>() {
                                @Override
                                public void onNext(UserResponse userResponse) {
                                    if (userResponse.getStatus().equals("success")) {
                                        view.loadMore(userResponse);
                                    } else {
//                                        view.statusError(userResponse.getStatus());
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("UserPresenter", "onError: " + e.getLocalizedMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            })
        );
    }

    public void detachView() {
        disposable.dispose();
    }

}
