package fr.frederic.picasaviewer4android.modules;

import com.google.inject.AbstractModule;

import fr.frederic.picasaviewer4android.models.pictures.PicturesModelImpl;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;

/**
 * Created by Frederic on 04/02/14.
 */
public class PictureModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PicturesModel.class).to(PicturesModelImpl.class);
    }
}
