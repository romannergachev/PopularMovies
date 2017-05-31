package com.rnergachev.popularmovies.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.TVShow;
import com.rnergachev.popularmovies.ui.presenter.TVShowActivityPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rnergachev on 30/05/2017.
 */

public class TVShowFragment extends Fragment {
    private final static String PAGER_POSITION = "PAGER_POSITION";

    public static TVShowFragment getInstance(int position) {
        TVShowFragment fragment = new TVShowFragment();
        Bundle args = new Bundle();
        args.putInt(PAGER_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.title_text_view) TextView title;
    @BindView(R.id.votes_text_view) TextView votes;
    @BindView(R.id.overview_text_view) TextView overview;
    @BindView(R.id.movie_poster_image_view) ImageView poster;


    private TVShow tvShow;

    @Inject
    TVShowActivityPresenter TVShowActivityPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this, rootView);

        PopularMoviesApplication application = (PopularMoviesApplication) getActivity().getApplication();
        application.appComponent.inject(this);

        int position = getArguments().getInt(PAGER_POSITION);

        tvShow = TVShowActivityPresenter.getTVShow(position);

        //show data on screen
        Picasso.with(getContext()).load(getString(R.string.image_base_url) + tvShow.getPosterPath()).into(poster);
        title.append(tvShow.getName());
        votes.append(String.valueOf(tvShow.getVoteAverage()) + getString(R.string.votes));
        overview.setText(tvShow.getOverview());

        if(position == 0) {
            poster.setTransitionName(getString(R.string.transition_image));
            poster.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    poster.getViewTreeObserver().removeOnPreDrawListener(this);
                    getActivity().startPostponedEnterTransition();
                    return true;
                }
            });
        }


        return rootView;
    }
}
