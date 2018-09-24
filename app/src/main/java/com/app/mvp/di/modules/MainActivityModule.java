package com.app.mvp.di.modules;

import com.app.mvp.http.MoreInfoApiService;
import com.app.mvp.http.MovieApiService;
import com.app.mvp.topmovies.MainActivityMVP;
import com.app.mvp.topmovies.MainActivityModel;
import com.app.mvp.topmovies.MainActivityPresenter;
import com.app.mvp.topmovies.MoviesRepository;
import com.app.mvp.topmovies.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public MainActivityMVP.Presenter providePresenter(MainActivityMVP.Model model) {
        return new MainActivityPresenter(model);
    }

    @Provides
    public MainActivityMVP.Model provideModel(Repository repository) {
        return new MainActivityModel(repository);
    }

    @Provides
    @Singleton
    public Repository provideRepository(MovieApiService movieApiService, MoreInfoApiService moreInfoApiService) {
        return new MoviesRepository(movieApiService, moreInfoApiService);
    }

}
