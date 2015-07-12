package com.tianyu.android.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Image;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Track;

import java.util.List;


/**
 * Created by andy on 7/11/15.
 */
public class TrackAdapter extends ArrayAdapter<Track> {
    private static final String LOG_TAG = TrackAdapter.class.getSimpleName();

    public TrackAdapter(Activity context, List<Track> tracks) {
        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Track track = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_thumbnail);

        if ( track.album.images != null &&  track.album.images.size() > 0){
            if (track.album.images.size() > 0) {
                Image image = track.album.images.get(track.album.images.size() - 1);
                Picasso.with(convertView.getContext()).load(image.url).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.placeholder);
            }
        }

        TextView albumNameView = (TextView) convertView.findViewById(R.id.list_item_album_name);
        albumNameView.setText(track.album.name);

        TextView trackNameView = (TextView) convertView.findViewById(R.id.list_item_track_name);
        trackNameView.setText(track.name);

        if (position % 2 == 0) {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.evenRowColor));
        } else {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.oddRowColor));
        }
        convertView.invalidate();

        return convertView;
    }
}
