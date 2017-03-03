package com.rnergachev.popularmovies.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.listener.EndlessRecyclerViewScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity with the list of the movies
 *
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity
    extends AppCompatActivity
    implements DiscoveryAdapter.DiscoveryAdapterHandler, AdapterView.OnItemSelectedListener
{
    @BindView(R.id.movies_list) RecyclerView movieRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.my_toolbar) Toolbar toolbar;
    @BindView(R.id.spinner_sort) Spinner spinner;
    private EndlessRecyclerViewScrollListener scrollListener;
    private GridLayoutManager gridLayoutManager;
    private int currentSort;
    private DiscoveryAdapter discoveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ButterKnife.bind(this);

        discoveryAdapter    = new DiscoveryAdapter(this, this, true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager   = new GridLayoutManager(this, BuildConfig.NUMBER_OF_COLUMNS_PORT);
        }
        else{
            gridLayoutManager   = new GridLayoutManager(this, BuildConfig.NUMBER_OF_COLUMNS_LAND);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //add and configure spinner
        currentSort = 0;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_array,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setAdapter(discoveryAdapter);

        //add view scroll listener to check the end of the list and fetch new data
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                discoveryAdapter.fetchMovies();
            }
        };
        movieRecyclerView.addOnScrollListener(scrollListener);

        discoveryAdapter.fetchMovies();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(getString(R.string.extra_movie), movie);
        startActivity(intent);
    }

    @Override
    public void onError(Throwable exception) {
        onFetchingEnded();
        Log.d(getClass().getName(), exception.getMessage());
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDismissError() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFetchingStarted() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchingEnded() {
        loadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (currentSort == position) {
            return;
        }
        gridLayoutManager.removeAllViews();
        currentSort = position;
        discoveryAdapter.clearAdapter(position == 0);
        discoveryAdapter.fetchMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
