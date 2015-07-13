package com.tianyu.android.spotifystreamer;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Track;

import java.util.List;

/**
 * Created by andy on 7/12/15.
 */
public interface FetchTrackListener {
    void processFinish(List<Track> result);
}
