package com.example.inventoria.ui.gudang.editor;

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

public class GudangPresenter {

    GudangView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public GudangPresenter(GudangView view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    void saveGudang( MultipartBody.Part foto, RequestBody email, RequestBody username, RequestBody password, RequestBody level, RequestBody nama, RequestBody
            tgl_lahir, RequestBody jenis_kelamin, RequestBody alamat, RequestBody no_telp) {
        view.showProgress();
        disposable.add(
                apiInterface.saveGudang(foto, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp)
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

    void updateGudang( String id_user, MultipartBody.Part foto, RequestBody email, RequestBody username, RequestBody password, RequestBody level, RequestBody nama, RequestBody
            tgl_lahir, RequestBody jenis_kelamin, RequestBody alamat, RequestBody no_telp) {
        view.showProgress();
        disposable.add(
                apiInterface.updateGudang(id_user, foto, email, username, password, level, nama, tgl_lahir, jenis_kelamin, alamat, no_telp)
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

    void deleteGudang( String id_user) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteGudang( id_user)
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
