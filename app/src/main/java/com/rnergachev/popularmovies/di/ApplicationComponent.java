package com.rnergachev.popularmovies.di;


import com.rnergachev.popularmovies.ui.activity.TVShowActivity;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.fragment.TVShowFragment;

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
    void inject(TVShowActivity tvShowActivity);
    void inject(TVShowFragment tvShowFragment);
}
