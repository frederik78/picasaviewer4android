package fr.frederic.picasaviewer4android.activities;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.google.inject.Inject;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.lists.ImageAdapter;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;
import fr.frederic.picasaviewer4android.vos.Album;
import fr.frederic.picasaviewer4android.vos.Picture;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Created by Frederic on 14/02/14.
 */
public class ImageActivity extends RoboActivity {

    @Inject
    private PicturesModel picturesModel;

    @InjectView(R.id.viewPager)
    private ViewPager viewPager;

    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            final long id = (long) bundle.get("imageId");
            final int position = (int) bundle.get("position");
            final Picture picture = picturesModel.getPicture(id);
            if (picture != null) {

                pagerAdapter = new ImageAdapter(this, picturesModel.getAllPictures((Album) bundle.get("album")));
                viewPager.setAdapter(pagerAdapter);
                viewPager.setCurrentItem(position, true);
            }
        }
    }


}
