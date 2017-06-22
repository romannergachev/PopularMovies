package com.rnergachev.popularmovies.ui.adapter;

import com.rnergachev.popularmovies.data.model.Review;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
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
 * Unit test for ReviewAdapter
 *
 * Created by rnergachev on 22/06/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewAdapterUnitTest {
    private ReviewAdapter adapter;
    @Mock
    private MovieApi movieApi;

    @Before
    public void setUp() {
        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(new Review());
        reviews.add(new Review());
        ReviewsResponse reviewsResponse = new ReviewsResponse(reviews);

        when(movieApi.getReviews(1)).thenReturn(Single.just(reviewsResponse));

        adapter = new ReviewAdapter(movieApi);
        adapter.setHandler(() -> {});
    }

    @Test
    public void presenter_fetchReviews() throws Exception {
        assertEquals(adapter.getItemCount(), 0);
        adapter.fetchReviews(1);
        assertEquals(adapter.getItemCount(), 2);

    }
}
