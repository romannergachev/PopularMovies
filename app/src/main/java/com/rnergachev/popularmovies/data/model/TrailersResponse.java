package com.rnergachev.popularmovies.data.model;

import java.util.List;

/**
 * Model class for Trailer request
 *
 * Created by rnergachev on 03/03/2017.
 */

public class TrailersResponse {
    private List<Trailer> results;

    public List<Trailer> getTrailers() {
        return results;
    }

}