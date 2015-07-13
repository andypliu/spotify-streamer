package com.tianyu.android.spotifystreamer;

import android.os.AsyncTask;

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

    FetchArtistListener mListenser = null;

    public FetchArtistSearchTask(FetchArtistListener listenser) {
        mListenser = listenser;
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
            Artist artist = new Artist("", "ERROR: " + e.getMessage(), null);
            pResults.add(artist);
        }

        return pResults;
    }

    @Override
    protected void onPostExecute(List<Artist> results) {
         mListenser.processFinish(results);
    }
}
