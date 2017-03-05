package com.rnergachev.popularmovies.ui.activity;

import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;
import com.rnergachev.popularmovies.ui.adapter.TrailerAdapter;
import com.rnergachev.popularmovies.ui.fragment.ReviewAdapterFragment;
import com.rnergachev.popularmovies.ui.fragment.TrailerAdapterFragment;
import com.rnergachev.popularmovies.ui.listener.EndlessRecyclerViewScrollListener;
import com.rnergachev.popularmovies.ui.presenter.MovieActivityPresenter;
import com.rnergachev.popularmovies.ui.view.MovieActivityView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity with the details of the selected movie
 *
 * Created by rnergachev on 27/01/2017.
 */

public class MovieActivity extends AppCompatActivity
        implements TrailerAdapterFragment.AdapterCallbacks, ReviewAdapterFragment.AdapterCallbacks, MovieActivityView {
    private Movie movie;

    @BindView(R.id.movie_poster_image_view) ImageView poster;
    @BindView(R.id.title_text_view) TextView title;
    @BindView(R.id.release_date_text_view) TextView date;
    @BindView(R.id.votes_text_view) TextView votes;
    @BindView(R.id.overview_text_view) TextView overview;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.trailers_list) RecyclerView trailersRecyclerView;
    @BindView(R.id.reviews_list) RecyclerView reviewsRecyclerView;
    @BindView(R.id.favorite) CheckBox favoriteCheckBox;

    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private TrailerAdapter trailerAdapter;
    private TrailerAdapterFragment trailerAdapterFragment;
    private ReviewAdapter reviewAdapter;
    private ReviewAdapterFragment reviewAdapterFragment;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Integer currentPosition;

    @Inject MovieActivityPresenter movieActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        PopularMoviesApplication application = (PopularMoviesApplication) getApplication();
        application.appComponent.inject(this);

        //configure toolbar

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.movies));
        toolbar.setNavigationOnClickListener(arrow -> onBackPressed());

        //restore movie from extras as parcelable
        movie = getIntent().getParcelableExtra(getString(R.string.extra_movie));

        currentPosition = null;

        //show data on screen
        Picasso.with(this).load(getString(R.string.image_base_url) + movie.getPosterPath()).into(poster);
        title.append(movie.getTitle());
        date.append(movie.getReleaseDate());
        votes.append(String.valueOf(movie.getVoteAverage()) + getString(R.string.votes));
        overview.setText(movie.getOverview());

        FragmentManager fm = getFragmentManager();
        trailerAdapterFragment = (TrailerAdapterFragment) fm.findFragmentByTag(getString(R.string.tag_trailer_adapter_fragment));
        reviewAdapterFragment = (ReviewAdapterFragment) fm.findFragmentByTag(getString(R.string.tag_review_adapter_fragment));

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (trailerAdapterFragment == null) {
            trailerAdapterFragment = new TrailerAdapterFragment();
            fm.beginTransaction().add(trailerAdapterFragment, getString(R.string.tag_trailer_adapter_fragment)).commit();
        }

        if (reviewAdapterFragment == null) {
            reviewAdapterFragment = new ReviewAdapterFragment();
            fm.beginTransaction().add(reviewAdapterFragment, getString(R.string.tag_review_adapter_fragment)).commit();
        }

        favoriteCheckBox.setOnClickListener(v -> movieActivityPresenter.updateFavoriteStatus(movie));
    }

    @Override
    protected void onStart() {
        super.onStart();
        movieActivityPresenter.onStart(this);
        movieActivityPresenter.getFavoriteStatus(movie);
    }

    @Override
    protected void onStop() {
        super.onStop();
        movieActivityPresenter.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.review_position), linearLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(getString(R.string.review_position));
    }

    /**
     * Sets review adapter
     *
     * @param  adapter   adapter to set
     * @param  isInitial is initial?
     */
    @Override
    public void setReviewAdapter(ReviewAdapter adapter, boolean isInitial) {
        reviewAdapter = adapter;

        linearLayoutManager = new LinearLayoutManager(this);

        reviewsRecyclerView.setLayoutManager(linearLayoutManager);
        reviewsRecyclerView.setAdapter(reviewAdapter);

        //add view scroll listener to check the end of the list and fetch new data
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                reviewAdapter.fetchReviews(movie.getId());
            }
        };
        reviewsRecyclerView.addOnScrollListener(scrollListener);

        if (isInitial) {
            reviewAdapter.fetchReviews(movie.getId());
        } else {
            if (currentPosition != null) {
                linearLayoutManager.scrollToPosition(currentPosition);
            }

        }
    }

    /**
     * Sets trailer restored or created trailer adapter
     *
     * @param  adapter   adapter to set
     * @param  isInitial is initial?
     */
    @Override
    public void setTrailerAdapter(TrailerAdapter adapter, boolean isInitial) {
        trailerAdapter = adapter;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager   = new GridLayoutManager(this, BuildConfig.NUMBER_OF_COLUMNS_PORT);
        }
        else{
            gridLayoutManager   = new GridLayoutManager(this, BuildConfig.NUMBER_OF_COLUMNS_LAND);
        }

        trailersRecyclerView.setLayoutManager(gridLayoutManager);
        trailersRecyclerView.setAdapter(trailerAdapter);

        if(isInitial) {
            trailerAdapter.fetchTrailers(movie.getId());
        }
    }

    /**
     * Set favorite state
     *
     * @param  state favorite state
     */
    @Override
    public void setFavoriteState(boolean state) {
        favoriteCheckBox.setChecked(state);
    }
}
