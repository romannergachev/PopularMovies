package com.rnergachev.popularmovies;

import android.app.Application;

import com.rnergachev.popularmovies.di.ApplicationComponent;
import com.rnergachev.popularmovies.di.ApplicationModule;
import com.rnergachev.popularmovies.di.DaggerApplicationComponent;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by rnergachev on 03/03/2017.
 */

public class PopularMoviesApplication extends Application {

    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(false);
        Picasso.setSingletonInstance(built);
    }

}
