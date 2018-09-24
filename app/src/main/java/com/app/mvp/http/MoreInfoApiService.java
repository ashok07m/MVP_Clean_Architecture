package com.app.mvp.http;

import com.app.mvp.http.apimodel.Omdbapi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoreInfoApiService {

    @GET("/")
    Observable<Omdbapi> getCountry(@Query("t") String title);

}
