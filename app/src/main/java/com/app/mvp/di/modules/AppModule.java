package com.app.mvp.di.modules;

import android.app.Application;
import android.content.Context;

import com.app.mvp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }


    @Provides
    @Singleton
    public Context provideAppContet() {
        return mApplication;
    }
}
