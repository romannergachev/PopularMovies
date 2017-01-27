package com.rnergachev.popularmovies.ui.presenter;

import android.util.Log;

import com.rnergachev.popularmovies.data.network.MovieApi;
import com.rnergachev.popularmovies.ui.view.DiscoveryActivityView;

import javax.inject.Inject;

/**
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivityPresenter {
    private DiscoveryActivityView view;
    @Inject
    MovieApi movieApi;
    public void fetchPopularMovies() {
        int page = 1;
        movieApi.popularMovies(page).subscribe(
            list -> Log.d("gsdfgds", "gsdfgsdgf"), error -> Log.d(error.getMessage(), error.getMessage()), () -> Log.d("sfdf", "fdsf")
        );
    }
}
