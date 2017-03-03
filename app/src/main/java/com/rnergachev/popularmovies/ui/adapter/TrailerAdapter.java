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
import com.rnergachev.popularmovies.data.model.Trailer;
import com.rnergachev.popularmovies.data.network.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Created by rnergachev on 03/03/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private List<Trailer> trailerList;
    private Context context;
    @Inject MovieApi movieApi;

    public TrailerAdapter(Context context) {
        this.context = context;
        trailerList = new ArrayList<>();
    }

    public interface DiscoveryAdapterHandler {
        /**
         * Performs the movie selection
         *
         * @param  movie that has been selected
         */
        void onClick(Movie movie);
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
    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_thumbnail_image_view)
        ImageView trailerThumbnail;

        TrailerAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
        }
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discovery_list_item, parent, false);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        //String movieUrl = movieList.get(position).getPosterPath();
        //icasso.with(context).load(context.getString(R.string.image_base_url) + movieUrl).into(holder.movieThumbnail);
    }

    @Override
    public int getItemCount() {
//        if (null == movieList) return 0;
//        return movieList.size();
        return 0;
    }

    /**
     * Fetches the movies (popular or top rated) and add them to the movieList
     *
     */
    public void fetchMovies() {
//        if (currentPage == maxPage) {
//            return;
//        }
//        handler.onDismissError();
//        handler.onFetchingStarted();
//        currentPage++;
//        Log.d(getClass().getName(), "Fetching page: " + currentPage);
//        Observable<MoviesResponse> request;
//        if (isPopularSort) {
//            request = movieApi.popularMovies(currentPage);
//        } else {
//            request = movieApi.topRatedMovies(currentPage);
//        }
//
//        request.subscribe(
//                response -> {
//                    movieList.addAll(response.getMovies());
//                    this.notifyDataSetChanged();
//                    maxPage = response.getTotalPages();
//                    handler.onFetchingEnded();
//                },
//                handler::onError
//        );

    }
}
