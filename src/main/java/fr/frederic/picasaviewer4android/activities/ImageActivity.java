package fr.frederic.picasaviewer4android.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import fr.frederic.picasaviewer4android.R;
import roboguice.activity.RoboActivity;

/**
 * Created by Frederic on 14/02/14.
 */
public class ImageActivity extends RoboActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Drawable drawable = (Drawable) savedInstanceState.get("image");

        final ImageView imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setImageDrawable(drawable);
    }
}
