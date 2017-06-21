package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.model.TrailersResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit service describing The Movie DB API
 * <p>
 * Created by rnergachev on 27/01/2017.
 */

public interface MovieService {

    /**
     * Returns popular movies as an Single object
     *
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    @GET("movie/popular")
    Single<MoviesResponse> popularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    /**
     * Returns top rated movies as an Single object
     *
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Single<MoviesResponse>}
     */
    @GET("movie/top_rated")
    Single<MoviesResponse> topRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    /**
     * Returns trailers for the selected movie
     *
     * @param  apiKey  api key
     * @param  movieId movie id
     * @return        {@link Single<TrailersResponse>}
     */
    @GET("movie/{movie_id}/videos")
    Single<TrailersResponse> getTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    /**
     * Returns reviews for the selected movie
     *
     * @param  apiKey  api key
     * @param  movieId movie id
     * @return        {@link Single<ReviewsResponse>}
     */
    @GET("movie/{movie_id}/reviews")
    Single<ReviewsResponse> getReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}
