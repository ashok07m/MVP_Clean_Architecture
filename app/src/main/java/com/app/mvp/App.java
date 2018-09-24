package com.app.mvp;

import android.app.Application;

import com.app.mvp.di.components.AppComponent;
import com.app.mvp.di.components.DaggerAppComponent;
import com.app.mvp.di.modules.AppModule;
import com.app.mvp.di.modules.MovieApiModule;

public class App extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .movieApiModule(new MovieApiModule())
                .build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
