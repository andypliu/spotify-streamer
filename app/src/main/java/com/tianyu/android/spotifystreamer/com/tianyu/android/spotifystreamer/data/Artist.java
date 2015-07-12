package com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by andy on 7/11/15.
 */
public class Artist implements Parcelable {

    public String id;
    public String name;
    public ArrayList<Image> images;

    public Artist(String id, String name, ArrayList<Image> images) {
        this.name = name;
        this.id = id;
        this.images = images;
    }

    public Artist(Parcel source) {
        id = source.readString();
        name = source.readString();
        images = source.readArrayList(Image.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeArray(images.toArray());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }

        @Override
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }
    };
}


