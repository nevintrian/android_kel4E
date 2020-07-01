package com.example.inventoria.ui.konfirmasi.search;

import com.example.inventoria.network.ApiClient;
import com.example.inventoria.network.ApiInterface;
import com.example.inventoria.network.response.KeluarResponse;
import com.example.inventoria.ui.keluar.search.SearchView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter {
    com.example.inventoria.ui.keluar.search.SearchView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SearchPresenter(SearchView view) {
        this.view = view;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getSearch(String search) {
        view.showProgress();
        disposable.add(
                apiInterface.getKeluar(search)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KeluarResponse>(){
                            @Override
                            public void onNext(KeluarResponse keluarResponse) {
                                view.statusSuccess(keluarResponse);
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
