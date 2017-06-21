package com.rnergachev.popularmovies.di;

import com.rnergachev.popularmovies.ui.activity.DiscoveryActivity;
import com.rnergachev.popularmovies.ui.activity.MovieActivity;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;
import com.rnergachev.popularmovies.ui.adapter.TrailerAdapter;
import com.rnergachev.popularmovies.ui.fragment.DiscoveryAdapterFragment;
import com.rnergachev.popularmovies.ui.fragment.ReviewAdapterFragment;
import com.rnergachev.popularmovies.ui.fragment.TrailerAdapterFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application component for dagger
 *
 * Created by rnergachev on 03/03/2017.
 */



@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    // inject context and movieDbService
    //todo inject using fragment/activity
    void inject(DiscoveryAdapterFragment discoveryAdapterFragment);
    void inject(ReviewAdapterFragment reviewAdapterFragment);
    void inject(TrailerAdapterFragment trailerAdapterFragment);

    // inject realm instances
    void inject(MovieActivity movieActivity);
    void inject(DiscoveryActivity discoveryActivity);
}
