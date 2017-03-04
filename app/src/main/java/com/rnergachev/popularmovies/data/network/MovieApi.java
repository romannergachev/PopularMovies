package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.model.TrailersResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Movie API calling MovieDbService
 *
 * Created by rnergachev on 27/01/2017.
 */

@Singleton
public class MovieApi {
    private MovieDbService movieDbService;

    @Inject
    public MovieApi(MovieDbService movieDbService) {
        this.movieDbService = movieDbService;
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
     * Returns trailers as an Observable object
     *
     * @param  movieId   movie id
     * @return        {@link Observable<TrailersResponse>}
     */
    public Observable<TrailersResponse> getTrailers(int movieId) {
        return applySchedulers(movieDbService.getTrailers(BuildConfig.THE_MOVIE_DB_API_KEY, movieId));
    }

    /**
     * Returns reviews as an Observable object
     *
     * @param  movieId   movie id
     * @return        {@link Observable<ReviewsResponse>}
     */
    public Observable<ReviewsResponse> getReviews(int movieId) {
        return applySchedulers(movieDbService.getReviews(BuildConfig.THE_MOVIE_DB_API_KEY, movieId));
    }

    /**
     * Adds schedulers to the call
     *
     * @param  observable object to add schedulers
     * @return        {@link Observable<MoviesResponse>}
     */
    private <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
