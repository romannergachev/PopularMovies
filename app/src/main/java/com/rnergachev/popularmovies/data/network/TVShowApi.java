package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.model.TVShowResponse;
import com.rnergachev.popularmovies.data.model.TrailersResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
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
     * Returns popular movies as a Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    public Single<MoviesResponse> popularMovies(int page) {
        return applySchedulers(movieDbService.popularMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns top rated movies as a Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    public Single<MoviesResponse> topRatedMovies(int page) {
        return applySchedulers(movieDbService.topRatedMovies(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns top rated tv shows as a Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<TVShowResponse>}
     */
    public Single<TVShowResponse> topRatedTVShows(int page) {
        return applySchedulers(movieDbService.topRatedTVShows(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns similar tv shows as a Single object
     *
     * @param  tvId   movie id
     * @return        {@link Single<TVShowResponse>}
     */
    public Single<TVShowResponse> similarTVShows(int tvId, int page) {
        return applySchedulers(movieDbService.similarTVShows(tvId, BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns trailers as a Single object
     *
     * @param  movieId   movie id
     * @return        {@link Single<TrailersResponse>}
     */
    public Single<TrailersResponse> getTrailers(int movieId) {
        return applySchedulers(movieDbService.getTrailers(movieId, BuildConfig.THE_MOVIE_DB_API_KEY));
    }

    /**
     * Returns reviews as a Single object
     *
     * @param  movieId   movie id
     * @return        {@link Single<ReviewsResponse>}
     */
    public Single<ReviewsResponse> getReviews(int movieId) {
        return applySchedulers(movieDbService.getReviews(movieId, BuildConfig.THE_MOVIE_DB_API_KEY));
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
