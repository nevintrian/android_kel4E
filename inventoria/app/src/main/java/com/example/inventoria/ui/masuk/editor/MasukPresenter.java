package com.example.inventoria.ui.masuk.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.MasukResponse;
import com.example.inventoria.network.response.SupplierResponse;
import com.example.inventoria.ui.masuk.editor.MasukView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MasukPresenter {

    com.example.inventoria.ui.masuk.editor.MasukView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public MasukPresenter(MasukView view) {
        this.view = view;
        disposable = new CompositeDisposable();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }


    void getListSupplier() {
        view.showProgress();
        disposable.add(
                apiInterface.getSupplierList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SupplierResponse>(){
                            @Override
                            public void onNext(SupplierResponse supplierResponse) {
                                view.setListSupplier(supplierResponse);
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




    void saveMasuk(String id_barang, String id_supplier, String tgl_masuk, String total_masuk) {
        view.showProgress();
        disposable.add(
                apiInterface.saveMasuk(id_barang, id_supplier, tgl_masuk, total_masuk)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MasukResponse>() {
                            @Override
                            public void onNext(MasukResponse masukResponse) {
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

    void updateMasuk(String id_masuk, String id_barang, String id_supplier, String tgl_masuk, String total_masuk) {
        view.showProgress();
        disposable.add(
                apiInterface.updateMasuk(id_masuk, id_barang, id_supplier, tgl_masuk, total_masuk)
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

    void deleteMasuk(String id_masuk) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteMasuk(id_masuk)
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
