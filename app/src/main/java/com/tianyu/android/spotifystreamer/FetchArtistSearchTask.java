package com.tianyu.android.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Artist;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Image;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;



/**
 * Created by andy on 7/10/15
 */
public class FetchArtistSearchTask extends AsyncTask<String, Void, List<Artist>>
{

    String LOG_TAG = FetchArtistSearchTask.class.getSimpleName();

    ArtistAdapter mArtistAdapter;
    Context mContext;

    public FetchArtistSearchTask(Context context, ArtistAdapter artistAdapter) {
        mArtistAdapter = artistAdapter;
        mContext = context;
    }

    @Override
    protected List<Artist> doInBackground(String... params) {
        List<Artist> pResults = new ArrayList<>();

        try {
            SpotifyApi api = new SpotifyApi();
            SpotifyService service = api.getService();
            String artistName = params[0];
            ArtistsPager results = service.searchArtists(artistName);
            for (kaaes.spotify.webapi.android.models.Artist artist : results.artists.items) {
                ArrayList<Image> pImages = new ArrayList<>();

                if (artist.images != null && artist.images.size() > 0) {
                    for (kaaes.spotify.webapi.android.models.Image image : artist.images) {
                        Image pImage = new Image(image.height, image.width, image.url);
                        pImages.add(pImage);
                    }
                }

                Artist pArtist = new Artist(artist.id, artist.name, pImages);
                pResults.add(pArtist);
            }
        } catch(Exception e) {
            Toast.makeText(mContext, "ERROR: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        return pResults;
    }

    @Override
    protected void onPostExecute(List<Artist>result) {
        if (result != null) {
            mArtistAdapter.clear();

            if (result.size() == 0) {
                Artist artist = new Artist("", mContext.getString(R.string.artist_error_message), null);
                mArtistAdapter.add(artist);
            } else {
                for (Artist artist : result) {
                    mArtistAdapter.add(artist);
                }
            }
        }
    }
}
