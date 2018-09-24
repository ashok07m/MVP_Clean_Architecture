package com.app.mvp.di.components;

import com.app.mvp.di.modules.AppModule;
import com.app.mvp.di.modules.MainActivityModule;
import com.app.mvp.di.modules.MovieApiModule;
import com.app.mvp.topmovies.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, MovieApiModule.class, MainActivityModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
