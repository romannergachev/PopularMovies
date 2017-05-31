package com.rnergachev.popularmovies.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.TVShow;
import com.rnergachev.popularmovies.data.network.TVShowApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for TV shows list
 *
 * Created by roman on 28.1.2017.
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryAdapterViewHolder> {
    private List<TVShow> tvShowList;
    private int currentPage;
    private int maxPage;
    private Context context;
    private final DiscoveryAdapterHandler handler;
    @Inject TVShowApi tvShowApi;

    public DiscoveryAdapter(Activity activity, DiscoveryAdapterHandler handler) {
        currentPage = 0;
        maxPage = Integer.MAX_VALUE;
        this.context = activity;
        this.handler = handler;
        tvShowList = new ArrayList<>();
        PopularMoviesApplication application = (PopularMoviesApplication) activity.getApplication();
        application.appComponent.inject(this);
    }

    public interface DiscoveryAdapterHandler {
        /**
         * Performs the tv show selection
         *
         * @param  tvShow that has been selected
         * @param view
         */
        void onClick(TVShow tvShow, View view);
        /**
         * Returns the error
         *
         * @param  exception error
         */
        void onError(Throwable exception);
        /**
         * Shows progress bar
         */
        void onFetchingStarted();
        /**
         * Hides progress bar
         */
        void onFetchingEnded();
        /**
         * Dismisses error
         */
        void onDismissError();
    }

    /**
     * ViewHolder class in order to hold and reuse previously inflated views
     */
    class DiscoveryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_show_thumbnail_image_view) ImageView tvShowThumbnail;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.vote_average) TextView voteAverage;

        DiscoveryAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            TVShow tvShow = tvShowList.get(adapterPosition);
            handler.onClick(tvShow, v.findViewById(R.id.tv_show_thumbnail_image_view));
        }
    }

    @Override
    public DiscoveryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discovery_list_item, parent, false);
        return new DiscoveryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoveryAdapterViewHolder holder, int position) {
        String tvShowUrl = tvShowList.get(position).getPosterPath();
        Picasso.with(context).load(context.getString(R.string.image_base_url) + tvShowUrl).into(holder.tvShowThumbnail);
        holder.name.setText(tvShowList.get(position).getName());
        holder.voteAverage.setText(String.valueOf(tvShowList.get(position).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        if (null == tvShowList) return 0;
        return tvShowList.size();
    }

    /**
     * Fetches the tv shows (popular or top rated, or favorites from DB) and add them to the tvShowList
     *
     */
    public void fetchTVShows() {
        if (currentPage == maxPage) {
            return;
        }
        handler.onDismissError();
        handler.onFetchingStarted();
        currentPage++;
        Log.d(getClass().getName(), "Fetching page: " + currentPage);

        tvShowApi.topRatedTVShows(currentPage).subscribe(
            response -> {
                tvShowList.addAll(response.getTVShows());
                this.notifyDataSetChanged();
                maxPage = response.getTotalPages();
                handler.onFetchingEnded();

            },
            handler::onError
        );

    }

    /**
     * Clears adapter before fetch data with a new sort type
     *
     * @param  sortType new sort type
     */
    public void clearAdapter(int sortType) {
        currentPage = 0;
        maxPage = Integer.MAX_VALUE;
        tvShowList = new ArrayList<>();
    }
}
