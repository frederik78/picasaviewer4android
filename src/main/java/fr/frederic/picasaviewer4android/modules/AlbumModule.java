package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModelImpl;

/**
 * Created by Frederic on 04/02/14.
 */
public class AlbumModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AlbumsModel.class).to(AlbumsModelImpl.class);
    }
}
