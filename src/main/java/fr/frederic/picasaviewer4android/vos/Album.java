package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by Frederic on 02/02/14.
 */
public class Album {

    private String albumName;

    private Drawable image;

    public Album(String albumName, Drawable image)
    {
        this.albumName = albumName;

        this.image = image;
    }


    public String getAlbumName() {
        return albumName;
    }

    public Drawable getImage() {
        return image;
    }
}
