package com.rnergachev.popularmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by roman on 28.1.2017.
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<DiscoveryAdapter.DiscoveryAdapterViewHolder> {
    private List<Movie> movieList;
    private int currentPage;
    private Context context;
    private final DiscoveryAdapterOnClickHandler clickHandler;

    public DiscoveryAdapter(Context context, DiscoveryAdapterOnClickHandler clickHandler) {
        currentPage = 1;
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public interface DiscoveryAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public class DiscoveryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView movieThumbnail;

        public DiscoveryAdapterViewHolder(View view) {
            super(view);
            movieThumbnail = (ImageView) view.findViewById(R.id.movie_thumbnail_image_view);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = movieList.get(adapterPosition);
            clickHandler.onClick(movie);
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
        StringBuilder url = new StringBuilder(context.getString(R.string.image_base_url)).append(movieUrl);
        Picasso.with(context).load(url.toString()).into(holder.movieThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == movieList) return 0;
        return movieList.size();
    }

    public void setWeatherData(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}
