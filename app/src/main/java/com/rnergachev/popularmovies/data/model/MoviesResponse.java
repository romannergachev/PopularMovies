package com.rnergachev.popularmovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for Popular Movies request
 *
 * Created by roman on 28.1.2017.
 */

public class MoviesResponse {
    private int page;
    private List<Movie> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<Movie> getMovies() {
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
