package com.rnergachev.popularmovies.ui.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;

import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.data.model.db.MovieContract;
import com.rnergachev.popularmovies.ui.view.MovieActivityView;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * MovieActivity presenter
 *
 * Created by rnergachev on 05/03/2017.
 */

@Singleton
public class MovieActivityPresenter {
    @Inject Context context;
    private MovieActivityView view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject MovieActivityPresenter() {
    }

    public void onStart(MovieActivityView view) {
        this.view = view;
    }

    public void onStop() {
        this.view = null;
        compositeDisposable.clear();
    }

    /**
     * Update the favorite status of the movie
     *
     * @param  movieToUpdate  movie
     */
    public void updateFavoriteStatus(Movie movieToUpdate) {
        compositeDisposable.add(Single.just(movieToUpdate).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                movie -> {
                    CursorLoader cursorLoader = new CursorLoader(context, MovieContract.MovieEntry.CONTENT_URI,
                            null, MovieContract.MovieEntry.getSqlSelectForId(movie.getId()), null, null);
                    Cursor c = cursorLoader.loadInBackground();

                    if (c.getCount() == 0) {
                        insertMovie(context, movie);
                    } else {
                        deleteMovie(context, movie.getId());
                    }
                },
                this::showError
        ));
    }

    /**
     * Check and provide favorite status for the current movie
     *
     * @param  currentMovie  movie
     */
    public void getFavoriteStatus(Movie currentMovie) {
        compositeDisposable.add(Single.just(currentMovie).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                movie -> {
                    CursorLoader cursorLoader = new CursorLoader(context, MovieContract.MovieEntry.CONTENT_URI,
                            null, MovieContract.MovieEntry.getSqlSelectForId(movie.getId()), null, null);
                    Cursor c = cursorLoader.loadInBackground();

                    if (c.getCount() != 0) {
                        view.setFavoriteState(true);
                    } else {
                        view.setFavoriteState(false);
                    }
                },
                this::showError
        ));
    }

    /**
     * Show error
     *
     * @param  exception  exception
     */
    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }

    private static ContentValues createMovieDBEntry(Movie movie) {
        ContentValues movieEntry = new ContentValues();
        movieEntry.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        movieEntry.put(MovieContract.MovieEntry.COLUMN_NAME, movie.getTitle());
        movieEntry.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        movieEntry.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        movieEntry.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieEntry.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        return movieEntry;
    }

    private void insertMovie(Context context, Movie movie) {
        ContentValues movieEntry = createMovieDBEntry(movie);
        context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieEntry);
    }

    private void deleteMovie(Context context, int movieId) {
        context.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = " + movieId, null);
    }

}
