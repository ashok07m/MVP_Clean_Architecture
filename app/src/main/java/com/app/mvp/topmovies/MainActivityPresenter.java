package com.app.mvp.topmovies;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private MainActivityMVP.Model model;
    private MainActivityMVP.View view;
    private Disposable subscription = null;


    public MainActivityPresenter(MainActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(MainActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {

        subscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ViewModel>() {
                    @Override
                    public void onNext(ViewModel viewModel) {
                        Log.d("loadData()",
                                "viewModel ::" + viewModel.getCountry() + " :: " + viewModel.getName());

                        if (view != null)
                            view.updateData(viewModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("loadData()", "error occured..");
                        if (view != null)
                            view.showSnackBar();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("loadData()", "loading complete..");
                    }
                });


    }

    @Override
    public void rxUnSubscribe() {
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }
}
