package com.rnergachev.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Activity with the details of the selected movie
 *
 * Created by rnergachev on 27/01/2017.
 */

public class MovieActivity extends AppCompatActivity {
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //configure toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        //restore movie from extras as parcelable
        movie = getIntent().getParcelableExtra(getString(R.string.extra_movie));

        ImageView poster  = (ImageView) findViewById(R.id.movie_poster_image_view);
        TextView title    = (TextView)  findViewById(R.id.title_text_view);
        TextView date     = (TextView)  findViewById(R.id.release_date_text_view);
        TextView votes    = (TextView)  findViewById(R.id.votes_text_view);
        TextView overview = (TextView)  findViewById(R.id.overview_text_view);

        //show data on screen
        Picasso.with(this).load(getString(R.string.image_base_url) + movie.getPosterPath()).into(poster);
        title.setText(movie.getTitle());
        date.setText(movie.getReleaseDate());
        votes.setText(String.valueOf(movie.getVoteAverage()));
        overview.setText(movie.getOverview());



    }
}
