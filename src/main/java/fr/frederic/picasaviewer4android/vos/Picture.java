package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Frederic on 08/02/14.
 */
public class Picture implements Serializable{

    private Drawable drawable;

    public Picture(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
