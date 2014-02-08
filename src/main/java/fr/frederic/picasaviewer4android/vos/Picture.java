package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;

/**
 * Created by Frederic on 08/02/14.
 */
public class Picture {

    private Drawable drawable;

    public Picture(Drawable drawable)
    {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
