package com.app.mvp.topmovies;

import com.app.mvp.http.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class MainActivityModel implements MainActivityMVP.Model {

    private Repository repository;

    public MainActivityModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<ViewModel> result() {
        return Observable.zip(repository.getResultsFromNetwork(), repository.getCountriesFromNetwork(), new BiFunction<Result, String, ViewModel>() {
            @Override
            public ViewModel apply(Result result, String s) throws Exception {
                return new ViewModel(result.getTitle(), s);
            }
        });

    }
}
