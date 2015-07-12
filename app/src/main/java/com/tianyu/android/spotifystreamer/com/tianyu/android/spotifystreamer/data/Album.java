package com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by andy on 7/11/15.
 */
public class Album implements Parcelable {

    public String name;
    public ArrayList<Image> images;

    public Album(String name, ArrayList<Image> images) {
        this.name = name;
        this.images = images;
    }

    public Album(Parcel source) {
        name = source.readString();
        images = source.readArrayList(Image.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeArray(images.toArray());
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }

        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }
    };
}

