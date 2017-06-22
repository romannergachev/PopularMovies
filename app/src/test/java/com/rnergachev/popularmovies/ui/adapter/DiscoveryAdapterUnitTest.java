package com.rnergachev.popularmovies.ui.adapter;

import android.view.View;

import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.network.MovieApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit test for DiscoveryAdapter
 *
 * Created by rnergachev on 22/06/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DiscoveryAdapterUnitTest {

    private DiscoveryAdapter adapter;
    @Mock
    private MovieApi movieApi;

    @Before
    public void setUp() {
        ArrayList<Movie> popularMovies = new ArrayList<>();
        popularMovies.add(new Movie(1, "poster", "overview", "releaseDate", "popularMovieFirst", 1.0, 2.0));
        popularMovies.add(new Movie(2, "poster", "overview", "releaseDate", "popularMovieSecond", 1.0, 2.0));
        MoviesResponse popularMoviesResponse = new MoviesResponse(popularMovies);

        ArrayList<Movie> topMovies = new ArrayList<>();
        topMovies.add(new Movie(1, "poster", "overview", "releaseDate", "popularMovieFirst", 1.0, 2.0));
        topMovies.add(new Movie(2, "poster", "overview", "releaseDate", "popularMovieSecond", 1.0, 2.0));
        MoviesResponse topMoviesResponse = new MoviesResponse(topMovies);

        when(movieApi.popularMovies(1)).thenReturn(Single.just(popularMoviesResponse));
        when(movieApi.topRatedMovies(1)).thenReturn(Single.just(topMoviesResponse));

        adapter = new DiscoveryAdapter(null, movieApi, null);
        adapter.setHandler(new DiscoveryAdapter.DiscoveryAdapterHandler() {
            @Override
            public void onClick(Movie movie, View view) {

            }

            @Override
            public void onError(Throwable exception) {

            }

            @Override
            public void onFetchingStarted() {

            }

            @Override
            public void onFetchingEnded() {

            }

            @Override
            public void onDismissError() {

            }

            @Override
            public void onDataUpdated() {

            }
        });
    }

    @Test
    public void presenter_fetchMovies() throws Exception {
        assertEquals(adapter.getItemCount(), 0);
        adapter.fetchMovies();
        assertEquals(adapter.getItemCount(), 2);

    }

    @Test
    public void presenter_setSortType() throws Exception {
        adapter.fetchMovies();
        adapter.setSortType(1);
        assertEquals(adapter.getItemCount(), 0);
    }
}
