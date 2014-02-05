package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.AlbumModel;
import fr.frederic.picasaviewer4android.models.AlbumModelImpl;

/**
 * Created by Frederic on 05/02/14.
 */
public class TestAlbumModule extends AbstractModule {

    final AlbumModel albumModel;

    public TestAlbumModule(AlbumModel albumModel)
    {
        this.albumModel = albumModel;
    }

    @Override
    protected void configure() {
        bind(AlbumModel.class).toInstance(albumModel);
    }
}
