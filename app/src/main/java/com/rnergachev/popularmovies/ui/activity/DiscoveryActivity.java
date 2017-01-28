package com.rnergachev.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.presenter.DiscoveryActivityPresenter;

/**
 * Activity with the list of the movies
 *
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity extends AppCompatActivity implements DiscoveryAdapter.DiscoveryAdapterOnClickHandler{
    private DiscoveryActivityPresenter presenter;

    private RecyclerView movieRecyclerView;
    private DiscoveryAdapter discoveryAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        presenter = new DiscoveryActivityPresenter(getString(R.string.base_url));

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        discoveryAdapter = new DiscoveryAdapter(this, this);

        movieRecyclerView = (RecyclerView) findViewById(R.id.movies_list);
        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieRecyclerView.setHasFixedSize(true);
        movieRecyclerView.setAdapter(discoveryAdapter);

        //loadWeatherData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.fetchPopularMovies();
    }

    @Override
    public void onClick(Movie movie) {
        //// TODO: 28.1.2017
    }

    private void showWeatherDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
