package com.rnergachev.popularmovies.component;


import com.rnergachev.popularmovies.module.ApplicationModule;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;
import com.rnergachev.popularmovies.ui.adapter.TrailerAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rnergachev on 03/03/2017.
 */



@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(DiscoveryAdapter discoveryAdapter);
    void inject(ReviewAdapter reviewAdapter);
    void inject(TrailerAdapter trailerAdapter);
}
