package com.rnergachev.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Movie model class for retrofit response
 *
 * Created by rnergachev on 27/01/2017.
 */

public class Movie extends RealmObject implements Parcelable {
    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    @PrimaryKey
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    private String title;
    private double popularity;
    @SerializedName("vote_average")
    private double voteAverage;

    public Movie() {

    }

    public Movie(int id, String posterPath, String overview, String releaseDate, String title, double popularity, double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title =title;
        this.popularity =  popularity;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
