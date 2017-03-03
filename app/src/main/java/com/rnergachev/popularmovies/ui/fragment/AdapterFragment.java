package com.rnergachev.popularmovies.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.rnergachev.popularmovies.ui.activity.DiscoveryActivity;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;

/**
 * Fragment for background tasks
 *
 * Created by rnergachev on 03/03/2017.
 */

public class AdapterFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */
    public interface AdapterCallbacks {
        void setAdapter(DiscoveryAdapter adapter);
    }

    private AdapterCallbacks callbacks;
    private DiscoveryAdapter discoveryAdapter;

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

        DiscoveryActivity discoveryActivity = (DiscoveryActivity) getActivity();

        // Create and execute the background task.
        discoveryAdapter = new DiscoveryAdapter(discoveryActivity, discoveryActivity, true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(discoveryAdapter != null) {
            callbacks.setAdapter(discoveryAdapter);
        }
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