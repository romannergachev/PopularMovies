package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.model.TrailersResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Movie API calling MovieService
 *
 * Created by rnergachev on 27/01/2017.
 */

@Singleton
public class MovieApi {
    private MovieService movieService;

    @Inject
    public MovieApi(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Returns popular movies as an Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    public Single<MoviesResponse> popularMovies(int page) {
        return applySchedulers(movieService.popularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns top rated movies as an Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    public Single<MoviesResponse> topRatedMovies(int page) {
        return applySchedulers(movieService.topRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns trailers as an Single object
     *
     * @param  movieId   movie id
     * @return        {@link Single<TrailersResponse>}
     */
    public Single<TrailersResponse> getTrailers(int movieId) {
        return applySchedulers(movieService.getTrailers(movieId, BuildConfig.THE_MOVIE_DB_API_KEY));
    }

    /**
     * Returns reviews as an Single object
     *
     * @param  movieId   movie id
     * @return        {@link Single<ReviewsResponse>}
     */
    public Single<ReviewsResponse> getReviews(int movieId) {
        return applySchedulers(movieService.getReviews(movieId, BuildConfig.THE_MOVIE_DB_API_KEY));
    }

    /**
     * Adds schedulers to the call
     *
     * @param  single object to add schedulers
     * @return        {@link Single<MoviesResponse>}
     */
    private <T> Single<T> applySchedulers(Single<T> single) {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}