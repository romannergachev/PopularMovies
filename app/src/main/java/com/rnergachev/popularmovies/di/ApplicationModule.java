package com.rnergachev.popularmovies.di;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.network.MovieDbService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application module for dagger
 *
 * Created by rnergachev on 03/03/2017.
 */
@Module
public class ApplicationModule {
    private Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }


    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }


    @Provides
    @Singleton
    public MovieDbService provideMovieDbService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(MovieDbService.class);
    }
}