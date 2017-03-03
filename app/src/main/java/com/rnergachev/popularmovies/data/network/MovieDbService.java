package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.model.TrailersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    /**
     * Returns trailers for the selected movie
     *
     * @param  apiKey  api key
     * @param  movieId movie id
     * @return        {@link Observable<TrailersResponse>}
     */
    @GET("movie/{movie_id}/videos")
    Observable<TrailersResponse> getTrailers(@Query("api_key") String apiKey, @Path("movie_id") int movieId);

    /**
     * Returns reviews for the selected movie
     *
     * @param  apiKey  api key
     * @param  movieId movie id
     * @return        {@link Observable<ReviewsResponse>}
     */
    @GET("movie/{movie_id}/reviews")
    Observable<ReviewsResponse> getReviews(@Query("api_key") String apiKey, @Path("movie_id") int movieId);

}
