package com.rnergachev.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.rnergachev.popularmovies.ui.activity.MovieActivity;
import com.rnergachev.popularmovies.ui.adapter.TrailerAdapter;

/**
 * Fragment for trailer background tasks
 *
 * Created by rnergachev on 03/03/2017.
 */

public class TrailerAdapterFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */
    public interface AdapterCallbacks {
        void setTrailerAdapter(TrailerAdapter adapter, boolean isInitial);
    }

    private AdapterCallbacks callbacks;
    private TrailerAdapter trailerAdapter;
    private boolean isInitial;

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        MovieActivity movieActivity = (MovieActivity) getActivity();

        // Create and execute the background task.
        trailerAdapter = new TrailerAdapter(movieActivity);
        isInitial = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        callbacks = (AdapterCallbacks) getActivity();

    }

    @Override
    public void onStop() {
        super.onStop();
        callbacks = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(trailerAdapter != null) {
            callbacks.setTrailerAdapter(trailerAdapter, isInitial);
        }
        isInitial = false;
    }
}