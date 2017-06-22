package com.rnergachev.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rnergachev.popularmovies.R;
import com.rnergachev.popularmovies.data.model.Review;
import com.rnergachev.popularmovies.data.model.ReviewsResponse;
import com.rnergachev.popularmovies.data.network.MovieApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;

/**
 * Adapter class for Reviews list
 * <p>
 * Created by rnergachev on 03/03/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private List<Review> reviewList;
    private int currentPage;
    private int maxPage;
    private MovieApi movieApi;
    private ReviewAdapterHandler handler;

    @Inject
    public ReviewAdapter(MovieApi movieApi) {
        currentPage = 0;
        this.movieApi = movieApi;
        maxPage = Integer.MAX_VALUE;
        reviewList = new ArrayList<>();
    }

    /**
     * ViewHolder class in order to hold and reuse previously inflated views
     */
    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author_text_view)
        TextView authorText;
        @BindView(R.id.content_text_view)
        TextView contentText;

        ReviewAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder holder, int position) {
        String content = reviewList.get(position).getContent();
        String author = reviewList.get(position).getAuthor();
        holder.authorText.setText(author);
        holder.contentText.setText(content);
    }

    @Override
    public int getItemCount() {
        if (null == reviewList) return 0;
        return reviewList.size();
    }

    /**
     * Fetches reviews and adds them to the list
     */
    public void fetchReviews(int movieId) {
        if (currentPage == maxPage) {
            return;
        }
        currentPage++;
        Single<ReviewsResponse> request = movieApi.getReviews(movieId);

        request.subscribe(
            response -> {
                reviewList.addAll(response.getReviews());
                handler.onReviewDataUpdated();
                maxPage = response.getTotalPages();
            },
            this::showError
        );

    }

    /**
     * Sets handler
     *
     * @param handler to set
     */
    public void setHandler(ReviewAdapterHandler handler) {
        this.handler = handler;
    }

    private void showError(Throwable exception) {
        Log.d(this.getClass().getName(), exception.getMessage());
    }

    public interface ReviewAdapterHandler {
        /**
         * Update reviews recyclerview with new data
         */
        void onReviewDataUpdated();
    }
}
