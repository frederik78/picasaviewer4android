package fr.frederic.picasaviewer4android.models.pictures;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import fr.frederic.picasaviewer4android.models.PictureModelListener;
import fr.frederic.picasaviewer4android.vos.Album;
import fr.frederic.picasaviewer4android.vos.Picture;

/**
 * Created by Frederic on 08/02/14.
 */
public class AbstractPicturesModel implements PicturesModel {

    protected Collection<PictureModelListener> pictureModelListeners = new HashSet<>();

    @Override
    public int getCount(Album album) {
        return 10;
    }

    @Override
    public List<Picture> getAllPictures(Album album) {
        final List<Picture> pictures = new ArrayList<>();
        final Drawable drawable = BitmapDrawable.createFromStream(getClass().getResourceAsStream("/drawable-mdpi/ic_launcher.png"), "ic_launcher.png");
        for (int i = 0; i < 10; i++) {
            pictures.add(new Picture(i, drawable));
        }
        return pictures;
    }

    @Override
    public Picture getPicture(long id) {
        final Drawable drawable = BitmapDrawable.createFromPath("/drawable-mdpi/ic_launcher.png");
        return new Picture(id, drawable);
    }

    @Override
    public void notifyUpdate() {
        for (PictureModelListener listener : pictureModelListeners) {
            listener.updateData();
        }
    }

    @Override
    public void addListener(PictureModelListener pictureModelListener) {
        pictureModelListeners.add(pictureModelListener);
    }
}
