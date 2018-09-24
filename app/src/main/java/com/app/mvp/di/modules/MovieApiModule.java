package com.app.mvp.di.modules;

import com.app.mvp.http.MoreInfoApiService;
import com.app.mvp.http.MovieApiService;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MovieApiModule {

    private final String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final String MOVIE_API_KEY = "2f7dbdf34f2986b02580ca1040b80a5a";

    private final String INFO_BASE_URL = " http://www.omdbapi.com";
    private final String INFO_API_KEY = "850348c7";

    @Provides
    public OkHttpClient provideHttpClient(final String key, final String value) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter(
                                key, value
                        ).build();

                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return client;
    }

    @Provides
    public Retrofit provideRetrofitClient(String baseURL, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    @Provides
    public MoreInfoApiService provideMoreInfoApiService() {
        return provideRetrofitClient(INFO_BASE_URL, provideHttpClient("apikey", INFO_API_KEY))
                .create(MoreInfoApiService.class);
    }

    @Provides
    public MovieApiService provideMovieApiService() {
        return provideRetrofitClient(MOVIE_BASE_URL, provideHttpClient("api_key", MOVIE_API_KEY))
                .create(MovieApiService.class);
    }

}
