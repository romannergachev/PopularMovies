package com.rnergachev.popularmovies.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.ui.adapter.DiscoveryAdapter;

import javax.inject.Inject;

/**
 * Fragment for retention of discovery adapter
 *
 * Created by rnergachev on 03/03/2017.
 */

public class DiscoveryAdapterFragment extends Fragment {

    /**
     * Callback interface through which the fragment will report the
     * task's progress and results back to the Activity.
     */
    public interface AdapterCallbacks {
        void setAdapter(DiscoveryAdapter adapter);
    }

    private AdapterCallbacks callbacks;
    @Inject DiscoveryAdapter discoveryAdapter;

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);

        ((PopularMoviesApplication) getActivity().getApplication()).appComponent.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        callbacks = (AdapterCallbacks) getActivity();
        if (discoveryAdapter != null) {
            discoveryAdapter.setHandler((DiscoveryAdapter.DiscoveryAdapterHandler) getActivity());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(discoveryAdapter != null) {
            callbacks.setAdapter(discoveryAdapter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        callbacks = null;
    }
}