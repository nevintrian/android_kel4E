package com.example.inventoria.ui.pelanggan.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;
import com.example.inventoria.ui.user.editor.UserView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PelangganPresenter {

    PelangganView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public PelangganPresenter(PelangganView view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    void savePelanggan(String email, String username, String password, String level, String nama, String tgl_lahir, String jenis_kelamin, String no_telp, String alamat) {
        view.showProgress();
        disposable.add(
                apiInterface.savePelanggan(email, username, password, level, nama, tgl_lahir, jenis_kelamin, no_telp, alamat)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>() {
                            @Override
                            public void onNext(UserResponse UserResponse) {
                                view.statusSuccess(UserResponse.getStatus());
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

    void updatePelanggan(String id_user, String email, String username, String password, String level, String nama, String tgl_lahir, String jenis_kelamin, String no_telp, String alamat) {
        view.showProgress();
        disposable.add(
                apiInterface.updatePelanggan(id_user, email, username, password, level, nama, tgl_lahir, jenis_kelamin, no_telp, alamat)
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
                                    view.hideProgress();
                                    view.statusError(e.getLocalizedMessage());
                                }
                            })
        );
    }

    void deletePelanggan(String id_user) {
        view.showProgress();
        disposable.add(
                apiInterface.deletePelanggan(id_user)
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
                                    view.hideProgress();
                                    view.statusError(e.getLocalizedMessage());
                                }
                            })
        );
    }

    public void detachView() {
        disposable.dispose();
    }

}
