package fr.frederic.picasaviewer4android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

/**
 * Created by Frederic on 08/02/14.
 */
public class PicturesActivity extends RoboActivity implements AdapterView.OnItemClickListener{

    @Inject
    private PicturesModel picturesModel;

    @InjectView(R.id.gridview)
    private GridView gridView;

    private Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture);
        final Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.get("album") != null)
        {
            album = (Album) bundle.get("album");
            final List<Picture> pictures = picturesModel.getAllPictures(album);
            gridView.setAdapter(new GridPictureAdapter(this, pictures));
            gridView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("imageId", id);
        intent.putExtra("album", album);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
