package fr.frederic.picasaviewer4android.models.albums;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.vos.Album;

/**
 * Created by Frederic on 03/02/14.
 */
public class AbstractAlbumsModel implements AlbumsModel {


    protected Collection<AlbumModelListener> albumModelListeners = new HashSet<>();

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public List<Album> getAllAlbums() {
        final List<Album> albums = new ArrayList<>();

        final Drawable drawable = BitmapDrawable.createFromStream(getClass().getResourceAsStream("/drawable-mdpi/ic_launcher.png"),"ic_launcher.png");
        for(int i =0; i < 10; i++)
        {
            albums.add(new Album(i, "album_" +i, drawable));
        }
        return albums;
    }

    @Override
    public Album getAlbum(long id) {
        final Drawable drawable = BitmapDrawable.createFromStream(getClass().getResourceAsStream("/drawable-mdpi/ic_launcher.png"),"ic_launcher.png");
        return new Album(id, "album_"+id, drawable);
    }

    @Override
    public void notifyUpdate() {
        for(AlbumModelListener listener : albumModelListeners)
        {
            listener.updateData();
        }
    }

    @Override
    public void addListener(AlbumModelListener albumModelListener) {
        albumModelListeners.add(albumModelListener);
    }
}
