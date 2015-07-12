package com.tianyu.android.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Artist;
import com.tianyu.android.spotifystreamer.com.tianyu.android.spotifystreamer.data.Image;

import java.util.List;

/**
 * Created by andy on 7/10/15.
 */
public class ArtistAdapter extends ArrayAdapter<Artist> {
    private static final String LOG_TAG = ArtistAdapter.class.getSimpleName();

    public ArtistAdapter(Activity context, List<Artist> artists) {
        super(context, 0, artists);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Artist artist = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_icon);

        if (artist.images != null) {
            imageView.setVisibility(View.VISIBLE);
            if(artist.images.size() > 0) {
                Image image = artist.images.get(artist.images.size() - 1);
                Picasso.with(convertView.getContext()).load(image.url).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.placeholder);
            }
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

        TextView artistNameView = (TextView) convertView.findViewById(R.id.list_item_artist_name);
        artistNameView.setText(artist.name);

        if (position % 2 == 0) {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.evenRowColor));
        } else {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.oddRowColor));
        }
        convertView.invalidate();
        return convertView;
    }
}
