package com.rnergachev.popularmovies.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;

import javax.inject.Inject;

/**
 * Fragment for retention of review adapter
 *
 * Created by rnergachev on 03/03/2017.
 */

public class ReviewAdapterFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */
    public interface AdapterCallbacks {
        void setReviewAdapter(ReviewAdapter adapter, boolean isInitial);
    }

    private AdapterCallbacks callbacks;
    @Inject ReviewAdapter reviewAdapter;
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
        isInitial = true;

        ((PopularMoviesApplication) getActivity().getApplication()).appComponent.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        callbacks = (AdapterCallbacks) getActivity();
        if (reviewAdapter != null) {
            reviewAdapter.setHandler((ReviewAdapter.ReviewAdapterHandler) getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(reviewAdapter != null) {
            callbacks.setReviewAdapter(reviewAdapter, isInitial);
        }
        isInitial = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        callbacks = null;
    }
}