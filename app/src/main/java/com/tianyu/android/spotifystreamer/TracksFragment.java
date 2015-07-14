package com.tianyu.android.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Album;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Artist;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TracksFragment extends Fragment {

    TrackAdapter mTrackAdapter;
    ArrayList<Track> mTracks;
    Context mContext;
    boolean fetchTrack = true;

    private String LOG_TAG = TracksFragment.class.getSimpleName();

    public TracksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getActivity().getString(R.string.tracks_key), mTracks);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState == null || !savedInstanceState.containsKey(getActivity().getString(R.string.tracks_key))) {
            mTracks = new ArrayList<>();
        } else {
            mTracks = savedInstanceState.getParcelableArrayList(getActivity().getString(R.string.tracks_key));
            fetchTrack = false;
        }

        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);
        mTrackAdapter = new TrackAdapter(getActivity(), mTracks);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_track);
        listView.setAdapter(mTrackAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Track track = mTrackAdapter.getItem(position);

                Toast.makeText(getActivity(), "Start playing " + track.name + " . . .",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Artist artist = null;
        if (fetchTrack) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_ASSIST_CONTEXT)) {

                artist = intent.getParcelableExtra(Intent.EXTRA_ASSIST_CONTEXT);
                FetchTrackSearchTask fetchTrackSearchTask = new FetchTrackSearchTask(new FetchTrackListener() {
                    @Override
                    public void processFinish(List<Track> result) {
                        if (result != null) {
                            mTrackAdapter.clear();

                            if (result.size() == 0) {
                                Track track = new Track(new Album(mContext.getString(R.string.track_error_message), null), "");
                                mTrackAdapter.add(track);
                            }
                            for (Track track : result) {
                                mTrackAdapter.add(track);
                            }
                        }
                    }
                });
                getActivity().setTitle(rootView.getContext().getString(R.string.title_activity_tracks) + " " + artist.name);

                fetchTrackSearchTask.execute(artist.id);
            }
        }

        final ViewGroup actionBarLayout = (ViewGroup) inflater.inflate(
                R.layout.track_title_bar,
                null);

        // Update the action bar title with artist's name
        final ActionBar actionBar = ((TracksActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView actionBarTitle = (TextView) actionBarLayout.findViewById(R.id.list_item_artist_name);
        actionBarTitle.setText(artist.name);
        return rootView;
    }
}
