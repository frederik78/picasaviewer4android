package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;

/**
* Created by Frederic on 05/02/14.
*/
public class TestPictureModule extends AbstractModule {

    final PicturesModel picturesModel;

    public TestPictureModule(PicturesModel picturesModel)
    {
        this.picturesModel = picturesModel;
    }

    @Override
    protected void configure() {
        bind(PicturesModel.class).toInstance(picturesModel);
    }
}
