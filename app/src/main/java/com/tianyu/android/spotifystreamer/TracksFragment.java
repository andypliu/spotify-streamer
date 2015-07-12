package com.tianyu.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Artist;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Track;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TracksFragment extends Fragment {

    TrackAdapter mTrackAdapter;
    ArrayList<Track> mTracks;
    boolean fetchTrack = true;

    private String LOG_TAG = TracksFragment.class.getSimpleName();

    public TracksFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if (fetchTrack) {
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_ASSIST_CONTEXT)) {

                Artist artist = intent.getParcelableExtra(Intent.EXTRA_ASSIST_CONTEXT);
                FetchTrackSearchTask fetchTrackSearchTask = new FetchTrackSearchTask(rootView.getContext(), mTrackAdapter);
                getActivity().setTitle("Top 10 Tracks " + artist.name);

                fetchTrackSearchTask.execute(artist.id);
            }
        }

        return rootView;
    }
}
