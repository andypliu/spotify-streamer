package com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andy on 7/11/15.
 */
public class Track implements Parcelable {

    public Album album;
    public String name;

    public Track(Album album, String name) {
        this.album = album;
        this.name = name;
    }

    public Track(Parcel source) {
        album = source.readParcelable(Album.class.getClassLoader());
        name = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(album, flags);
    }

    public String getName() {
        return name;
    }

    public Album getAlbum() {
        return album;
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }

        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }
    };
}

