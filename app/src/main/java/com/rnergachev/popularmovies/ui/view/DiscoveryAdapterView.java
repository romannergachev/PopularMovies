package com.rnergachev.popularmovies.ui.view;

import com.rnergachev.popularmovies.data.model.Movie;

import java.util.List;

/**
 * Created by rnergachev on 27/01/2017.
 */

public interface DiscoveryAdapterView {
    void moviesFetched(List<Movie> movies, int page);
}
