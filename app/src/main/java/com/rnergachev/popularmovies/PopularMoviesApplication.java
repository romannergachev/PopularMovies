package com.rnergachev.popularmovies;

import android.app.Application;

import com.rnergachev.popularmovies.component.ApplicationComponent;
import com.rnergachev.popularmovies.component.DaggerApplicationComponent;
import com.rnergachev.popularmovies.module.ApplicationModule;

/**
 * Created by rnergachev on 03/03/2017.
 */

public class PopularMoviesApplication extends Application {

    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

}
