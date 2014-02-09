package fr.frederic.picasaviewer4android.activities;

import android.os.Bundle;

import com.google.inject.Inject;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;
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
//        picturesModel.getAllPictures()
    }
}
