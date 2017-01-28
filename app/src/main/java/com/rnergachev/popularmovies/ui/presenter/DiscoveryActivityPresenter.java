package com.rnergachev.popularmovies.ui.presenter;

import android.util.Log;

import com.rnergachev.popularmovies.data.network.MovieApi;
import com.rnergachev.popularmovies.ui.view.DiscoveryAdapterView;

/**
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivityPresenter {
    private DiscoveryAdapterView view;

    MovieApi movieApi;

    public DiscoveryActivityPresenter(String baseUrl) {
        movieApi = new MovieApi(baseUrl);
    }

    public void fetchPopularMovies() {
        int page = 1;
        movieApi.popularMovies(page).subscribe(
            response -> view.moviesFetched(response.getMovies(), response.getPage()),
            error -> Log.d(getClass().getName(), error.getMessage())
        );
    }
}
