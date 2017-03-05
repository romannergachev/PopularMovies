package com.rnergachev.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Trailer model class for retrofit response
 *
 * Created by rnergachev on 03/03/2017.
 */

public class Trailer implements Parcelable {
    private String id;
    private String name;
    private String key;

    public Trailer(String id, String name, String key) {
        this.id = id;
        this.key = key;
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.key);
    }

    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }
}
