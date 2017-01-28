package com.rnergachev.popularmovies.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for retrofit response
 *
 * Created by rnergachev on 27/01/2017.
 */

public class Movie {
    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    private String title;
    private double popularity;
    @SerializedName("vote_average")
    private double voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
