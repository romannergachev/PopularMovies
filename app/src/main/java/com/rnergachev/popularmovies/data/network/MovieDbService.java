package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.data.model.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Service describing The Movie DB API
 *
 * Created by rnergachev on 27/01/2017.
 */

public interface MovieDbService {
    //    pages from 1 to 1000
    //http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
    @GET("movie/popular")
    Observable<MoviesResponse> pupularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<MoviesResponse> topRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);


}
