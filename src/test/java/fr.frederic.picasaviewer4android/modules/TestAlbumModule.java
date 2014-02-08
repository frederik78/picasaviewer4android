package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;

/**
 * Created by Frederic on 05/02/14.
 */
public class TestAlbumModule extends AbstractModule {

    final AlbumsModel albumsModel;

    public TestAlbumModule(AlbumsModel albumsModel)
    {
        this.albumsModel = albumsModel;
    }

    @Override
    protected void configure() {
        bind(AlbumsModel.class).toInstance(albumsModel);
    }
}
