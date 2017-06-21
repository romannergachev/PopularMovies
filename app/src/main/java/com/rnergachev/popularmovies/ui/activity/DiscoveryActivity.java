package com.rnergachev.popularmovies.ui.activity;

import android.app.FragmentManager;
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

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.fragment.DiscoveryAdapterFragment;
import com.rnergachev.popularmovies.ui.listener.EndlessRecyclerViewScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity with the list of the movies
 * <p>
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity
    extends AppCompatActivity
    implements DiscoveryAdapter.DiscoveryAdapterHandler, AdapterView.OnItemSelectedListener, DiscoveryAdapterFragment.AdapterCallbacks {
    @BindView(R.id.movies_list)
    RecyclerView movieRecyclerView;
    @BindView(R.id.tv_error_message_display)
    TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_sort)
    Spinner spinner;
    private EndlessRecyclerViewScrollListener scrollListener;
    private GridLayoutManager gridLayoutManager;
    private int currentSort;
    private int currentPosition;
    private DiscoveryAdapter discoveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentSort = 0;

        createSpinner();

        FragmentManager fm = getFragmentManager();
        DiscoveryAdapterFragment discoveryAdapterFragment = (DiscoveryAdapterFragment) fm.findFragmentByTag(getString(R.string.tag_adapter_fragment));

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (discoveryAdapterFragment == null) {
            discoveryAdapterFragment = new DiscoveryAdapterFragment();
            fm.beginTransaction().add(discoveryAdapterFragment, getString(R.string.tag_adapter_fragment)).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentSort == getResources().getInteger(R.integer.favorites) && discoveryAdapter != null) {
            discoveryAdapter.fetchMovies();
        }
        if (gridLayoutManager != null) {
            gridLayoutManager.scrollToPosition(currentPosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = gridLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.extra_position), gridLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(getString(R.string.sort_type), currentSort);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(getString(R.string.extra_position));
        currentSort = savedInstanceState.getInt(getString(R.string.sort_type));
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
        discoveryAdapter.setSortType(position);
        currentPosition = 0;
        if (currentSort != getResources().getInteger(R.integer.favorites)) {
            movieRecyclerView.addOnScrollListener(scrollListener);
        } else {
            movieRecyclerView.clearOnScrollListeners();
        }
        discoveryAdapter.fetchMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * Sets the adapter to the recycler view
     *
     * @param adapter restored or created adapter
     */
    @Override
    public void setAdapter(DiscoveryAdapter adapter) {
        discoveryAdapter = adapter;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.number_of_columns_port));
        } else {
            gridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.number_of_columns_land));
        }

        movieRecyclerView.setLayoutManager(gridLayoutManager);
        movieRecyclerView.setAdapter(discoveryAdapter);

        //add view scroll listener to check the end of the list and fetch new data
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                discoveryAdapter.fetchMovies();
            }
        };
        if (currentSort != getResources().getInteger(R.integer.favorites)) {
            movieRecyclerView.addOnScrollListener(scrollListener);
        }


        if (discoveryAdapter.getItemCount() > 0) {
            gridLayoutManager.scrollToPosition(currentPosition);
        } else {
            discoveryAdapter.fetchMovies();
        }
    }

    private void createSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort_array,
            android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
}
