package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Frederic on 08/02/14.
 */
public class Picture implements Serializable{

    private Drawable drawable;

    private long id;

    public Picture(long id, Drawable drawable)
    {
        this.id = id;
        this.drawable = drawable;
    }

    public long getId() {
        return id;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;

        Picture picture = (Picture) o;

        if (id != picture.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
