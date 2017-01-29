package com.rnergachev.popularmovies.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.data.model.MoviesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Movie API calling MovieDbService
 *
 * Created by rnergachev on 27/01/2017.
 */

public class MovieApi {
    private MovieDbService movieDbService;

    public MovieApi(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        movieDbService = retrofit.create(MovieDbService.class);
    }

    /**
     * Returns popular movies as an Observable object
     *
     * @param  page   number of current page
     * @return        {@link Observable<MoviesResponse>}
     */
    public Observable<MoviesResponse> popularMovies(int page) {
        return applySchedulers(movieDbService.popularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns top rated movies as an Observable object
     *
     * @param  page   number of current page
     * @return        {@link Observable<MoviesResponse>}
     */
    public Observable<MoviesResponse> topRatedMovies(int page) {
        return applySchedulers(movieDbService.topRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Adds schedulers to the call
     *
     * @param  observable object to add schedulers
     * @return        {@link Observable<MoviesResponse>}
     */
    private Observable<MoviesResponse> applySchedulers(Observable<MoviesResponse> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
