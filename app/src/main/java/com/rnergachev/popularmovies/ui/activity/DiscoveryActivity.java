package com.rnergachev.popularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.ui.presenter.DiscoveryActivityPresenter;
import com.rnergachev.popularmovies.ui.view.DiscoveryActivityView;

/**
 * Activity with the list of the movies
 *
 * Created by rnergachev on 27/01/2017.
 */

public class DiscoveryActivity extends AppCompatActivity implements DiscoveryActivityView {
    private DiscoveryActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
    }
}
