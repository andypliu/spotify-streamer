package com.tianyu.android.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Album;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Image;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by andy on 7/10/15.
 */
public class FetchTrackSearchTask extends AsyncTask<String, Void, List<Track>> {
    String LOG_TAG = FetchTrackSearchTask.class.getSimpleName();

    TrackAdapter mTrackAdapter;
    Context mContext;

    public FetchTrackSearchTask(Context context, TrackAdapter artistAdapter) {
        mTrackAdapter = artistAdapter;
        mContext = context;
    }

    @Override
    protected List<Track> doInBackground(String... params) {
        ArrayList<Track> pResults = new ArrayList<>();
        try {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();

            String artistId = params[0];
            Map<String, Object> map = new HashMap<>();
            map.put(mContext.getString(R.string.country_key), Locale.getDefault().getCountry());

            Tracks results = service.getArtistTopTrack(artistId, map);
            for (kaaes.spotify.webapi.android.models.Track track : results.tracks) {
                ArrayList<Image> pImages = new ArrayList<>();
                for (kaaes.spotify.webapi.android.models.Image image : track.album.images) {
                    Image pImage = new Image(image.height, image.width, image.url);
                    pImages.add(pImage);
                }

                Track pTrack = new Track(new Album(track.album.name, pImages), track.name);
                pResults.add(pTrack);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "ERROR: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        return pResults;
    }

    @Override
    protected void onPostExecute(List<Track> result) {
        if (result != null) {
            mTrackAdapter.clear();

            if (result.size() == 0) {
                Toast.makeText(mContext, mContext.getString(R.string.track_error_message),
                        Toast.LENGTH_SHORT).show();
            }
            for (Track track : result) {
                mTrackAdapter.add(track);
            }
        }
    }
}
