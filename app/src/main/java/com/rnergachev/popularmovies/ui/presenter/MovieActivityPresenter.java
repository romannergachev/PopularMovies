package com.rnergachev.popularmovies.ui.presenter;

import android.content.Context;
import android.util.Log;

import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.ui.view.MovieActivityView;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rnergachev on 05/03/2017.
 */

@Singleton
public class MovieActivityPresenter {
    @Inject Realm realm;
    private MovieActivityView view;

    @Inject MovieActivityPresenter() {
    }

    public void onStart(MovieActivityView view) {
        this.view = view;
    }

    public void onStop() {
        this.view = null;
    }

    public void updateFavoriteStatus(Movie movieToUpdate) {
        Observable.just(movieToUpdate).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                movie -> {
                    realm.beginTransaction();
                    RealmResults<Movie> movieFromRealm = realm.where(Movie.class).equalTo("id", movie.getId()).findAll();
                    if (movieFromRealm.size() == 0) {
                        realm.copyToRealm(movie); // Persist unmanaged objects
                    } else {
                        movieFromRealm.deleteAllFromRealm();
                    }
                    //view.update

                    realm.commitTransaction();
                },
                this::showError
        );
    }

    public void getFavoriteStatus(Movie currentMovie) {
        Observable.just(currentMovie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                movie -> {
                    RealmResults<Movie> movieFromRealmWW = realm.where(Movie.class).findAll();
                    RealmResults<Movie> movieFromRealm = realm.where(Movie.class).equalTo("id", movie.getId()).findAll();
                    if (movieFromRealm.size() != 0) {
                        view.setFavoriteState(true);
                    } else {
                        view.setFavoriteState(false);
                    }
                },
                this::showError
        );
    }

    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }

}
