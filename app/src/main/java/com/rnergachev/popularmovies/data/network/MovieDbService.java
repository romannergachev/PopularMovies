package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.data.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit service describing The Movie DB API
 * <p>
 * Created by rnergachev on 27/01/2017.
 */

interface MovieDbService {

    /**
     * Returns popular movies as an Observable object
     *
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Observable<MoviesResponse>}
     */
    @GET("movie/popular")
    Observable<MoviesResponse> popularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    /**
     * Returns top rated movies as an Observable object
     *
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Observable<MoviesResponse>}
     */
    @GET("movie/top_rated")
    Observable<MoviesResponse> topRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);
}
