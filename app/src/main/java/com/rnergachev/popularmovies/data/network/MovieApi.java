package com.rnergachev.popularmovies.data.network;

import android.content.res.Resources;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Movie API
 *
 * Created by rnergachev on 27/01/2017.
 */

public class MovieApi {
    private MovieDbService movieDbService;

    @Inject
    public MovieApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Resources.getSystem().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieDbService = retrofit.create(MovieDbService.class);
    }

    public Observable<List<Movie>> popularMovies(int page) {
        return applySchedulers(movieDbService.pupularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    public Observable<List<Movie>> topRatedMovies(int page) {
        return applySchedulers(movieDbService.topRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    private Observable<List<Movie>> applySchedulers(Observable<List<Movie>> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
