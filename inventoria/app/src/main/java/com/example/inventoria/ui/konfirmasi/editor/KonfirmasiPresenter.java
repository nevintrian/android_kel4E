package com.example.inventoria.ui.konfirmasi.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.KeluarResponse;
import com.example.inventoria.network.response.UserResponse;



import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KonfirmasiPresenter {

    KonfirmasiView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public KonfirmasiPresenter(KonfirmasiActivity view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }




    void getListUser() {
        view.showProgress();
        disposable.add(
                apiInterface.getUserList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>(){
                            @Override
                            public void onNext(UserResponse userResponse) {
                                view.setListUser(userResponse);
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



    void getListBarang() {
        view.showProgress();
        disposable.add(
                apiInterface.getBarangList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<BarangResponse>(){
                            @Override
                            public void onNext(BarangResponse barangResponse) {
                                view.setListBarang(barangResponse);
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






    void saveKonfirmasi(String id_barang, String id_user, String tgl_keluar, String total_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.saveKonfirmasi(id_barang, id_user, tgl_keluar, total_keluar)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KeluarResponse>() {
                            @Override
                            public void onNext(KeluarResponse keluarResponse) {
                                view.hideProgress();
                                view.statusSuccess("berhasil ditambahkan");
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





    void updateKonfirmasi(String id_keluar, String id_barang, String id_user, String tgl_keluar, String total_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.updateKonfirmasi(id_keluar, id_barang, id_user, tgl_keluar, total_keluar)
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

    void deleteKonfirmasi(String id_keluar) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteKonfirmasi(id_keluar)
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
