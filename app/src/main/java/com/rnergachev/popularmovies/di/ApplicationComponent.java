package com.rnergachev.popularmovies.di;

import com.rnergachev.popularmovies.ui.activity.DiscoveryActivity;
import com.rnergachev.popularmovies.ui.activity.MovieActivity;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;
import com.rnergachev.popularmovies.ui.adapter.TrailerAdapter;

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
    void inject(DiscoveryAdapter discoveryAdapter);
    void inject(ReviewAdapter reviewAdapter);
    void inject(TrailerAdapter trailerAdapter);

    // inject realm instances
    void inject(MovieActivity movieActivity);
    void inject(DiscoveryActivity discoveryActivity);
}
