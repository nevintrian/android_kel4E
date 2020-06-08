package com.example.inventoria.ui.keluar.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.KeluarResponse;
import com.example.inventoria.ui.keluar.editor.KeluarView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KeluarPresenter {

    KeluarView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public KeluarPresenter(KeluarView view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    void saveKeluar(String id_user, String tgl_keluar, String total_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.saveKeluar(id_user, tgl_keluar, total_keluar)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KeluarResponse>() {
                            @Override
                            public void onNext(KeluarResponse keluarResponse) {
                                view.statusSuccess(keluarResponse.getMessage());
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

    void updateKeluar(String id_keluar, String id_user, String tgl_keluar, String total_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.updateKeluar(id_keluar, id_user, tgl_keluar, total_keluar)
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

    void deleteKeluar(String id_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteKeluar(id_keluar)
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
