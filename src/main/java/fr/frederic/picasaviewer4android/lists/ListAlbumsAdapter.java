package fr.frederic.picasaviewer4android.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.vos.Album;

/**
 * Created by Frederic on 02/02/14.
 */
public class ListAlbumsAdapter extends BaseAdapter {

    final LayoutInflater inflater;

    final List<Album> albums;

    public ListAlbumsAdapter(Context context, List<Album> albums) {
        this.albums = albums;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return albums.size();
    }

    @Override
    public Object getItem(int position) {
        return albums.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.album_row, null);
            final TextView albumName = (TextView) convertView.findViewById(R.id.album_name);
            final ImageView image = (ImageView) convertView.findViewById(R.id.image);

            holder = new Holder(albumName, image);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.getAlbumName().setText(albums.get(position).getAlbumName());
        holder.getImage().setImageDrawable(albums.get(position).getImage());
        return convertView;
    }

private class Holder {
    ImageView image;

    TextView albumName;

    public Holder(TextView albumName, ImageView image) {
        this.albumName = albumName;
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getAlbumName() {
        return albumName;
    }
}
}
