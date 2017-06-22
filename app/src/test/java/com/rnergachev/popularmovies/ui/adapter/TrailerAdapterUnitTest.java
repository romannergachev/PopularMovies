package com.rnergachev.popularmovies.ui.adapter;

import com.rnergachev.popularmovies.data.model.Trailer;
import com.rnergachev.popularmovies.data.model.TrailersResponse;
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
 * Unit test for TrailerAdapter
 *
 * Created by rnergachev on 22/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TrailerAdapterUnitTest {
    private TrailerAdapter adapter;
    @Mock
    private MovieApi movieApi;

    @Before
    public void setUp() {
        ArrayList<Trailer> trailers = new ArrayList<>();
        trailers.add(new Trailer());
        TrailersResponse trailersResponse = new TrailersResponse(trailers);

        when(movieApi.getTrailers(1)).thenReturn(Single.just(trailersResponse));

        adapter = new TrailerAdapter(null, movieApi);
        adapter.setHandler(() -> {});
    }

    @Test
    public void presenter_fetchReviews() throws Exception {
        assertEquals(adapter.getItemCount(), 0);
        adapter.fetchTrailers(1);
        assertEquals(adapter.getItemCount(), 1);

    }
}
