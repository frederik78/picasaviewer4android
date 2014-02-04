package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.AlbumModel;
import fr.frederic.picasaviewer4android.models.AlbumModelImpl;

/**
 * Created by Frederic on 04/02/14.
 */
public class AlbumModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AlbumModel.class).to(AlbumModelImpl.class);
    }
}
