package com.rnergachev.popularmovies.ui.activity;

import android.content.Intent;
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

/**
 * Activity with the list of the movies
 *
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity
    extends AppCompatActivity
    implements DiscoveryAdapter.DiscoveryAdapterHandler, AdapterView.OnItemSelectedListener
{
    private RecyclerView movieRecyclerView;
    private DiscoveryAdapter discoveryAdapter;
    private TextView errorMessageDisplay;
    private ProgressBar loadingIndicator;
    private EndlessRecyclerViewScrollListener scrollListener;
    private GridLayoutManager gridLayoutManager;
    private int currentSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        Toolbar toolbar     = (Toolbar) findViewById(R.id.my_toolbar);
        errorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        loadingIndicator    = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        movieRecyclerView   = (RecyclerView) findViewById(R.id.movies_list);
        Spinner spinner     = (Spinner) findViewById(R.id.spinner_sort);

        discoveryAdapter    = new DiscoveryAdapter(this, this, true);
        gridLayoutManager   = new GridLayoutManager(this, BuildConfig.NUMBER_OF_COLUMNS);

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
