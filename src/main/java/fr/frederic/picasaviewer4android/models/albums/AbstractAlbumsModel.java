package fr.frederic.picasaviewer4android.models.albums;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.util.TechnicalException;
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
    public List<Album> getAllAlbums(String username) throws TechnicalException, IOException, ServiceException {
        final List<Album> albums = new ArrayList<>();

        final Drawable drawable = BitmapDrawable.createFromStream(getClass().getResourceAsStream("/drawable-mdpi/ic_launcher.png"),"ic_launcher.png");
        for(int i =0; i < 10; i++)
        {
            albums.add(new Album(Integer.valueOf(i).toString(), "album_" +i, drawable));
        }
        return albums;
    }

    @Override
    public Album getAlbum(long id) {
        final Drawable drawable = BitmapDrawable.createFromStream(getClass().getResourceAsStream("/drawable-mdpi/ic_launcher.png"),"ic_launcher.png");
        return new Album(Long.valueOf(id).toString(), "album_"+id, drawable);
    }

    @Override
    public void notifyUpdate() throws TechnicalException {
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
