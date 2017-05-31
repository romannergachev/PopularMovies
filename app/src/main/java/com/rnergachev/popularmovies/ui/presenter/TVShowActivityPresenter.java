package com.rnergachev.popularmovies.ui.presenter;

import android.util.Log;

import com.rnergachev.popularmovies.data.model.TVShow;
import com.rnergachev.popularmovies.data.network.TVShowApi;
import com.rnergachev.popularmovies.ui.activity.TVShowActivity;
import com.rnergachev.popularmovies.ui.view.TVShowView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * TVShowActivity presenter
 *
 * Created by rnergachev on 05/03/2017.
 */

@Singleton
public class TVShowActivityPresenter {

    @Inject
    TVShowActivityPresenter() {
        currentPage = 0;
        currentShow = 0;
        maxPage = Integer.MAX_VALUE;
        tvShowList = new ArrayList<>();
    }

    TVShowView view;
    private List<TVShow> tvShowList;
    private int maxPage;
    private int currentPage;
    private int currentShow;
    private TVShow tvShow;
    @Inject TVShowApi tvShowApi;

    /**
     * Show error
     *
     * @param  exception  exception
     */
    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }

    public void setView(TVShowView view) {
        this.view = view;
    }

    public void setTVShow(TVShow tvShow) {
        this.tvShow = tvShow;
        tvShowList.clear();
        currentPage = 0;
        currentShow = 0;
        maxPage = Integer.MAX_VALUE;
        tvShowList.add(tvShow);
    }

    public void fetchSimilarShows(int showId) {
        if (currentPage == maxPage) {
            return;
        }
        currentPage++;
        Log.d(getClass().getName(), "Fetching page: " + currentPage);

        tvShowApi.similarTVShows(showId, currentPage).subscribe(
            response -> {
                tvShowList.addAll(response.getTVShows());
                maxPage = response.getTotalPages();
                view.notifyDataChanged();
            },
            this::showError
        );
    }

    public int getCount() {
        return tvShowList.size();
    }

    public TVShow getTVShow(int position) {
        return tvShowList.get(position);
    }
}
