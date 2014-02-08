package fr.frederic.picasaviewer4android.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import fr.frederic.picasaviewer4android.vos.Picture;

/**
 * Created by Frederic on 08/02/14.
 */
public class GridPictureAdapter extends BaseAdapter {

    final LayoutInflater inflater;

    final List<Picture> pictures;

    public GridPictureAdapter(Context context, List<Picture> pictures)
    {
        this.pictures = pictures;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(inflater.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(position);
        return imageView;
    }
}
