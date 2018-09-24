package com.app.mvp.topmovies;

import com.app.mvp.http.apimodel.Result;

import io.reactivex.Observable;

public interface Repository {

    Observable<Result> getResultsFromNetwork();

    Observable<String> getCountriesFromNetwork();
}
