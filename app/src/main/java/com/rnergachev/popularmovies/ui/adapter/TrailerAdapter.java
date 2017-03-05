package com.rnergachev.popularmovies.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rnergachev.popularmovies.PopularMoviesApplication;
import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Trailer;
import com.rnergachev.popularmovies.data.model.TrailersResponse;
import com.rnergachev.popularmovies.data.network.MovieApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * Adapter class for Trailers list
 *
 * Created by rnergachev on 03/03/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {
    private List<Trailer> trailerList;
    private Context context;
    @Inject MovieApi movieApi;

    public TrailerAdapter(Activity activity) {
        this.context = activity;
        trailerList = new ArrayList<>();
        PopularMoviesApplication application = (PopularMoviesApplication) activity.getApplication();
        application.appComponent.inject(this);
    }

    /**
     * ViewHolder class in order to hold and reuse previously inflated views
     */
    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_thumbnail_image_view)
        ImageView trailerThumbnail;

        TrailerAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerList.get(adapterPosition).getKey())));
            Log.i(getClass().getName(), "Starting video trailer...");
        }
    }

    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerAdapter.TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        String trailerThumbnailUrl = context.getString(R.string.utube_thmb_path) + trailerList.get(position).getKey() + context.getString(R.string.utube_thmb);
        Picasso.with(context).load(trailerThumbnailUrl).into(holder.trailerThumbnail);
    }

    @Override
    public int getItemCount() {
        if (null == trailerList) return 0;
        return trailerList.size();
    }

    /**
     * Fetches trailers and add them to the list
     *
     */
    public void fetchTrailers(int movieId) {
        Log.d(getClass().getName(), "Fetching trailers... ");
        Observable<TrailersResponse> request = movieApi.getTrailers(movieId);

        request.subscribe(
                response -> {
                    trailerList.addAll(response.getTrailers());
                    this.notifyDataSetChanged();
                },
                this::showError
        );

    }

    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }
}
