package com.rnergachev.popularmovies.ui.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rnergachev.popularmovies.BuildConfig;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.TVShow;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.fragment.DiscoveryAdapterFragment;
import com.rnergachev.popularmovies.ui.listener.EndlessRecyclerViewScrollListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity with the list of the tv shows
 *
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity
    extends AppCompatActivity
    implements DiscoveryAdapter.DiscoveryAdapterHandler, DiscoveryAdapterFragment.AdapterCallbacks
{
    @BindView(R.id.tv_show_list) RecyclerView tvShowsRecyclerView;
    @BindView(R.id.tv_error_message_display) TextView errorMessageDisplay;
    @BindView(R.id.pb_loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.my_toolbar) Toolbar toolbar;
    private EndlessRecyclerViewScrollListener scrollListener;
    private GridLayoutManager gridLayoutManager;
    private Integer currentPosition;
    private DiscoveryAdapter discoveryAdapter;
    private DiscoveryAdapterFragment discoveryAdapterFragment;

    private static final int NUMBER_OF_COLUMNS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

        ButterKnife.bind(this);

        currentPosition = null;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.tv_shows);

        FragmentManager fm = getFragmentManager();
        discoveryAdapterFragment = (DiscoveryAdapterFragment) fm.findFragmentByTag(getString(R.string.tag_adapter_fragment));

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (discoveryAdapterFragment == null) {
            discoveryAdapterFragment = new DiscoveryAdapterFragment();
            fm.beginTransaction().add(discoveryAdapterFragment, getString(R.string.tag_adapter_fragment)).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = gridLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (gridLayoutManager!= null && currentPosition != null) {
            gridLayoutManager.scrollToPosition(currentPosition);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.extra_position), gridLayoutManager.findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt(getString(R.string.extra_position));
    }

    @Override
    public void onClick(TVShow tvShow, View view) {
        Intent intent = new Intent(this, TVShowActivity.class);
        intent.putExtra(getString(R.string.extra_tv_show), tvShow);

        ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(this, view, getString(R.string.transition_image));
        startActivity(intent, options.toBundle());
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

    /**
     * Sets the adapter to the recycler view
     *
     * @param  adapter restored or created adapter
     */
    @Override
    public void setAdapter(DiscoveryAdapter adapter) {
        discoveryAdapter   = adapter;

        gridLayoutManager   = new GridLayoutManager(this, NUMBER_OF_COLUMNS);


        tvShowsRecyclerView.setLayoutManager(gridLayoutManager);
        tvShowsRecyclerView.setAdapter(discoveryAdapter);

        //tvShowsRecyclerView.addItemDecoration(new RecyclerViewItemDecorator(0));

        //add view scroll listener to check the end of the list and fetch new data
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                discoveryAdapter.fetchTVShows();
            }
        };
        tvShowsRecyclerView.addOnScrollListener(scrollListener);


        if (currentPosition != null) {
            gridLayoutManager.scrollToPosition(currentPosition);
        } else {
            discoveryAdapter.fetchTVShows();
        }
    }
}
