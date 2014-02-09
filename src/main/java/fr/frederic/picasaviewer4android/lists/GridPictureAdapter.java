package fr.frederic.picasaviewer4android.lists;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
        final Holder holder;

        if (convertView == null) {

            convertView = new ImageView(inflater.getContext());
            convertView.setLayoutParams(new GridView.LayoutParams(85, 85));
            ((ImageView)convertView).setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setPadding(8, 8, 8, 8);
            ((ImageView)convertView).setImageDrawable(pictures.get(position).getDrawable());
            holder = new Holder(((ImageView)convertView));
            convertView.setTag(holder);
        } else {
            holder = ((Holder) convertView.getTag());
        }

        holder.getImageView().setImageDrawable(pictures.get(position).getDrawable());

        return convertView;
    }

    private static class Holder{

        private ImageView imageView;

        public Holder(ImageView imageView)
        {
            this.imageView = imageView;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }
}
