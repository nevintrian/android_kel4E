package com.example.inventoria.ui.user.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserPresenter {

    UserView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public UserPresenter(UserView view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    void saveUser( MultipartBody.Part foto, RequestBody email, RequestBody username, RequestBody password, RequestBody level, RequestBody nama, RequestBody
            tgl_lahir, RequestBody jenis_kelamin, RequestBody alamat, RequestBody no_telp) {
        view.showProgress();
        disposable.add(
                apiInterface.saveUser(foto, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>() {
                            @Override
                            public void onNext(UserResponse userResponse) {
                                view.statusSuccess(userResponse.getMessage());
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.statusError(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    void updateUser( String id_user, MultipartBody.Part foto, RequestBody email, RequestBody username, RequestBody password, RequestBody level, RequestBody nama, RequestBody
            tgl_lahir, RequestBody jenis_kelamin, RequestBody alamat, RequestBody no_telp) {
        view.showProgress();
        disposable.add(
                apiInterface.updateUser(id_user, foto, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){
                            @Override
                            public void onComplete() {
                                view.hideProgress();
                                view.statusSuccess("berhasil update");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.statusError(e.getLocalizedMessage());
                            }
                        })
        );
    }

    void deleteUser( String id_user) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteUser( id_user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){
                            @Override
                            public void onComplete() {
                                view.hideProgress();
                                view.statusSuccess("berhasil delete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.statusError(e.getLocalizedMessage());
                            }
                        })
        );
    }

    public void detachView() {
        disposable.dispose();
    }
}
