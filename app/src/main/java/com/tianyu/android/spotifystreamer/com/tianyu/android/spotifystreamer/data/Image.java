package com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andy on 7/11/15.
 */
public class Image implements Parcelable {

    public int height;
    public int width;
    public String url;

    public Image(int height, int width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public Image(Parcel source) {
        height = source.readInt();
        width = source.readInt();
        url = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(url);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }

        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }
    };
}


