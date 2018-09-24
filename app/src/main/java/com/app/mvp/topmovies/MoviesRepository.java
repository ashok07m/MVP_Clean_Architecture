package com.app.mvp.topmovies;

import com.app.mvp.http.MoreInfoApiService;
import com.app.mvp.http.MovieApiService;
import com.app.mvp.http.apimodel.Omdbapi;
import com.app.mvp.http.apimodel.Result;
import com.app.mvp.http.apimodel.TopRated;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoviesRepository implements Repository {

    private MovieApiService movieApiService;
    private MoreInfoApiService moreInfoApiService;
    private List<String> countries;
    private List<Result> results;

    public MoviesRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService) {
        this.movieApiService = movieApiService;
        this.moreInfoApiService = moreInfoApiService;
        countries = new ArrayList<>();
        results = new ArrayList<>();
    }

    @Override
    public Observable<Result> getResultsFromNetwork() {

        Observable<TopRated> topRatedMovies = movieApiService.getTopRatedMovies(1);
        return topRatedMovies.concatMap(new Function<TopRated, Observable<Result>>() {
            @Override
            public Observable<Result> apply(TopRated topRated) {
                return Observable.fromIterable(topRated.results);
            }
        }).doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result result) {
                results.add(result);
            }
        });
    }

    @Override
    public Observable<String> getCountriesFromNetwork() {

        return getResultsFromNetwork().concatMap(new Function<Result, ObservableSource<Omdbapi>>() {
            @Override
            public ObservableSource<Omdbapi> apply(Result result) throws Exception {
                return moreInfoApiService.getCountry(result.getTitle());
            }
        }).concatMap(new Function<Omdbapi, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Omdbapi omdbapi) throws Exception {
                return Observable.just(omdbapi.getCountry());
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                countries.add(s);
            }
        });

    }

}
