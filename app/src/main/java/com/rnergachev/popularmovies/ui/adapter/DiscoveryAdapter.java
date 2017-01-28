package com.rnergachev.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.rnergachev.popularmovies.data.model.MoviesResponse;
import com.rnergachev.popularmovies.data.network.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Adapter class for Movies list
 *
 * Created by roman on 28.1.2017.
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryAdapterViewHolder> {
    private List<Movie> movieList;
    private int currentPage;
    private int maxPage;
    private Context context;
    private final DiscoveryAdapterHandler handler;
    private boolean isPopularSort;
    private MovieApi movieApi;

    public DiscoveryAdapter(Context context, DiscoveryAdapterHandler handler, boolean isPopularSort) {
        currentPage = 0;
        maxPage = Integer.MAX_VALUE;
        this.context = context;
        this.handler = handler;
        movieApi = new MovieApi(context.getString(R.string.base_url));
        movieList = new ArrayList<>();
        this.isPopularSort = isPopularSort;
    }

    public interface DiscoveryAdapterHandler {
        void onClick(Movie movie);
        void onError(Throwable exception);
        void onFetchingStarted();
        void onFetchingEnded();
        void onDismissError();
    }

    class DiscoveryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView movieThumbnail;

        DiscoveryAdapterViewHolder(View view) {
            super(view);
            movieThumbnail = (ImageView) view.findViewById(R.id.movie_thumbnail_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movieList.get(adapterPosition);
            handler.onClick(movie);
        }
    }

    @Override
    public DiscoveryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discovery_list_item, parent, false);
        return new DiscoveryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoveryAdapterViewHolder holder, int position) {
        String movieUrl = movieList.get(position).getPosterPath();
        Picasso.with(context).load(context.getString(R.string.image_base_url) + movieUrl).into(holder.movieThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public void fetchMovies() {
        if (currentPage == maxPage) {
            return;
        }
        handler.onDismissError();
        handler.onFetchingStarted();
        currentPage++;
        Log.d(getClass().getName(), "Fetching page: " + currentPage);
        Observable<MoviesResponse> request;
        if (isPopularSort) {
            request = movieApi.popularMovies(currentPage);
        } else {
            request = movieApi.topRatedMovies(currentPage);
        }

        request.subscribe(
            response -> {
                movieList.addAll(response.getMovies());
                this.notifyDataSetChanged();
                maxPage = response.getTotalPages();
                handler.onFetchingEnded();
            },
            handler::onError
        );

    }

    public void clearAdapter(boolean isPopularSort) {
        currentPage = 0;
        maxPage = Integer.MAX_VALUE;
        movieList = new ArrayList<>();
        this.isPopularSort = isPopularSort;
    }
}
