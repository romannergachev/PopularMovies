package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.data.model.TVShowResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit service describing The Movie DB API
 * <p>
 * Created by rnergachev on 27/01/2017.
 */

public interface TVShowDbService {

    /**
     * Returns top rated tv shows as an Single object
     *
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Single<TVShowResponse>}
     */
    @GET("tv/top_rated")
    Single<TVShowResponse> topRatedTVShows(@Query("api_key") String apiKey, @Query("page") int page);

    /**
     * Returns similar tv shows as an Single object
     *
     * @param  tvId   tv show id
     * @param  apiKey api key
     * @param  page   number of current page
     * @return        {@link Single<TVShowResponse>}
     */
    @GET("tv/{tv_id}/similar")
    Single<TVShowResponse> similarTVShows(@Path("tv_id") int tvId, @Query("api_key") String apiKey, @Query("page") int page);


}
