package com.example.inventoria.ui.barang.editor;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.network.response.SupplierResponse;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BarangPresenter {

    BarangView view;
    CompositeDisposable disposable;
    ApiInterface apiInterface;

    public BarangPresenter(BarangView view) {
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


    void saveBarang( MultipartBody.Part foto_barang, RequestBody id_supplier, RequestBody nama_barang, RequestBody kemasan, RequestBody merk, RequestBody jenis, RequestBody
            stok, RequestBody harga, RequestBody terjual) {
        view.showProgress();
        disposable.add(
            apiInterface.saveBarang(foto_barang, id_supplier, nama_barang, kemasan, merk, jenis, stok, harga, terjual)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BarangResponse>() {
                    @Override
                    public void onNext(BarangResponse barangResponse) {
                        view.statusSuccess(barangResponse.getMessage());
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

    void updateBarang( String id_barang, MultipartBody.Part foto_barang, RequestBody id_supplier, RequestBody nama_barang, RequestBody kemasan, RequestBody merk, RequestBody jenis, RequestBody
            stok, RequestBody harga, RequestBody terjual) {
        view.showProgress();
        disposable.add(
                apiInterface.updateBarang(id_barang, foto_barang, id_supplier, nama_barang, kemasan, merk, jenis, stok, harga, terjual)
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

    void deleteBarang( String id_barang) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteBarang( id_barang)
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
