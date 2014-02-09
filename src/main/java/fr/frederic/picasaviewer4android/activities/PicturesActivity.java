package fr.frederic.picasaviewer4android.activities;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.GridView;

import com.google.inject.Inject;

import java.util.List;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.lists.GridPictureAdapter;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;
import fr.frederic.picasaviewer4android.vos.Album;
import fr.frederic.picasaviewer4android.vos.Picture;
import roboguice.activity.RoboActivity;

/**
 * Created by Frederic on 08/02/14.
 */
public class PicturesActivity extends RoboActivity {

    @Inject
    private PicturesModel picturesModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        final Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.get("album") != null)
        {
            final List<Picture> pictures = picturesModel.getAllPictures((Album) bundle.get("album"));
            final GridView gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new GridPictureAdapter(this, pictures));
        }
    }
}
