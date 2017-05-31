package com.rnergachev.popularmovies.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for TVShows request
 *
 * Created by roman on 28.1.2017.
 */

public class TVShowResponse {
    private int page;
    private List<TVShow> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<TVShow> getTVShows() {
        return results;
    }

    public int getTotalTVShows() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPage() {
        return page;
    }
}
