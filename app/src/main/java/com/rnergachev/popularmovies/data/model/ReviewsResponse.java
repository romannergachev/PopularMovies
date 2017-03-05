package com.rnergachev.popularmovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for Review request
 *
 * Created by rnergachev on 03/03/2017.
 */

public class ReviewsResponse {
    private int page;
    private List<Review> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<Review> getReviews() {
        return results;
    }

    public int getTotalMovies() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }
}
