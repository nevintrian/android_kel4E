package com.example.inventoria.ui.home;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.BarangResponse;
import com.example.inventoria.ui.barang.BarangView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    com.example.inventoria.ui.home.HomeView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public HomePresenter(HomeFragment view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getBarang() {
        view.showProgress();
        disposable.add(
                apiInterface.getBarang()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<BarangResponse>(){
                            @Override
                            public void onNext(BarangResponse barangResponse) {
                                view.statusSuccess(barangResponse);
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
