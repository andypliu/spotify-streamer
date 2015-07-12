package com.tianyu.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Artist;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    String LOG_TAG = SearchFragment.class.getSimpleName();
    EditText mSearchText;
    ArrayList<Artist> mArtists;
    ArtistAdapter mArtistAdapter;
    boolean mFetchData = true;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(getActivity().getString(R.string.artists_key), mArtists);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        if (savedInstanceState == null || !savedInstanceState.containsKey(getActivity().getString(R.string.artists_key))) {
            mArtists = new ArrayList<>();
        } else {
            mArtists = savedInstanceState.getParcelableArrayList(getActivity().getString(R.string.artists_key));
            mFetchData = false;
        }

        addTextChangedListener(rootView);
        mArtistAdapter = new ArtistAdapter(getActivity(), mArtists);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_artist);
        listView.setAdapter(mArtistAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Artist artist = mArtistAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), TracksActivity.class)
                        .putExtra(Intent.EXTRA_ASSIST_CONTEXT, artist);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void addTextChangedListener(final View view) {
        // get mSearchText component
        mSearchText = (EditText) view.findViewById(R.id.search);

        mSearchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mFetchData && s.length() > 0) {
                    FetchArtistSearchTask fetchSearchTask = new FetchArtistSearchTask(view.getContext(), mArtistAdapter);
                    fetchSearchTask.execute(s.toString());
                } else {
                    mFetchData = true;
                }
            }
        });
    }
}
