package com.example.inventoria.ui.user;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter {

    UserView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public UserPresenter(UserView view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getUser() {
        view.showProgress();
        disposable.add(
                apiInterface.getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>(){
                            @Override
                            public void onNext(UserResponse userResponse) {
                                view.statusSuccess(userResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
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
