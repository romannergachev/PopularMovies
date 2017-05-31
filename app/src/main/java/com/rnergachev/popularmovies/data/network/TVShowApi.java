package com.rnergachev.popularmovies.data.network;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.data.model.TVShowResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Movie API calling TVShowDbService
 *
 * Created by rnergachev on 27/01/2017.
 */

@Singleton
public class TVShowApi {
    private TVShowDbService TVShowDbService;

    @Inject
    public TVShowApi(TVShowDbService TVShowDbService) {
        this.TVShowDbService = TVShowDbService;
    }

    /**
     * Returns top rated tv shows as a Single object
     *
     * @param  page   number of current page
     * @return        {@link Single<TVShowResponse>}
     */
    public Single<TVShowResponse> topRatedTVShows(int page) {
        return applySchedulers(TVShowDbService.topRatedTVShows(BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Returns similar tv shows as a Single object
     *
     * @param  tvId   tv show id
     * @return        {@link Single<TVShowResponse>}
     */
    public Single<TVShowResponse> similarTVShows(int tvId, int page) {
        return applySchedulers(TVShowDbService.similarTVShows(tvId, BuildConfig.THE_MOVIE_DB_API_KEY, page));
    }

    /**
     * Adds schedulers to the call
     *
     * @param  single object to add schedulers
     * @return        {@link Single<T>}
     */
    private <T> Single<T> applySchedulers(Single<T> single) {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
