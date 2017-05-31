package com.rnergachev.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * TVShows model class for retrofit response
 *
 * Created by rnergachev on 27/01/2017.
 */

public class TVShow implements Parcelable {
    //Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    private String name;
    private double popularity;
    @SerializedName("vote_average")
    private double voteAverage;

    public TVShow() {

    }

    public TVShow(int id, String posterPath, String overview, String releaseDate, String title, double popularity, double voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.name =title;
        this.popularity =  popularity;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getName() {
        return name;
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
        dest.writeString(this.name);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
    }

    protected TVShow(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.name = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
