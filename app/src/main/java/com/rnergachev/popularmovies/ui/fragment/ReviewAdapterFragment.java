package com.rnergachev.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.rnergachev.popularmovies.ui.activity.MovieActivity;
import com.rnergachev.popularmovies.ui.adapter.ReviewAdapter;

/**
 * Fragment for review background tasks
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
    private ReviewAdapter reviewAdapter;
    private boolean isInitial;

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (AdapterCallbacks) context;
    }

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
        reviewAdapter = new ReviewAdapter(movieActivity);
        isInitial = true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(reviewAdapter != null) {
            callbacks.setReviewAdapter(reviewAdapter, isInitial);
        }
        isInitial = false;
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }
}