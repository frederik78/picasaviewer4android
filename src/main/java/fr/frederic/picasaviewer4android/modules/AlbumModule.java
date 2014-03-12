package fr.frederic.picasaviewer4android.modules;

import com.google.gdata.client.photos.PicasawebService;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModelImpl;
import fr.frederic.picasaviewer4android.util.Constantes;

/**
 * Created by Frederic on 04/02/14.
 */
public class AlbumModule extends AbstractModule {

    Provider<PicasawebService> picasawebServiceProvider = new Provider<PicasawebService>()
    {
        @Override
        public PicasawebService get() {
            return new PicasawebService(Constantes.APPLICATION_NAME);
        }
    };


    @Override
    protected void configure() {
        bind(PicasawebService.class).toProvider(picasawebServiceProvider);
        bind(AlbumsModel.class).to(AlbumsModelImpl.class);

    }


}



