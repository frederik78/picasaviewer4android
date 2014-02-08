package fr.frederic.picasaviewer4android.activities;

import android.os.Bundle;

import fr.frederic.picasaviewer4android.R;
import roboguice.activity.RoboActivity;

/**
 * Created by Frederic on 08/02/14.
 */
public class PicturesActivity extends RoboActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
    }
}
