package fr.frederic.picasaviewer4android.lists;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import fr.frederic.picasaviewer4android.vos.Picture;

/**
 * Created by Frederic on 15/02/14.
 */
public class ImageAdapter extends PagerAdapter {

    final LayoutInflater inflater;

    final List<Picture> pictures;

    public ImageAdapter(Context context, List<Picture> pictures)
    {
        this.inflater = LayoutInflater.from(context);
        this.pictures = pictures;
    }


    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ImageView imageView = new ImageView(inflater.getContext());
        imageView.setImageDrawable(pictures.get(position).getDrawable());
        ((ViewPager) container).addView(imageView, 0);
        container.setTag(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
