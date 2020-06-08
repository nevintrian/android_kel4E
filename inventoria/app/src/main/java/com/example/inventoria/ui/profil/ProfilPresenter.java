package com.example.inventoria.ui.profil;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.UserResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfilPresenter {

    ProfilActivity view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public ProfilPresenter(ProfilActivity view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }



    void updateProfil(String id_user, String email, String username, String password, String level, String nama, String tgl_lahir, String jenis_kelamin, String no_telp, String alamat) {
        view.showProgress();
        disposable.add(
                apiInterface.updateProfil(id_user, email, username, password, level, nama, tgl_lahir, jenis_kelamin, no_telp, alamat)
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


    public void detachView() {
        disposable.dispose();
    }

}
