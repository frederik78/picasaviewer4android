package fr.frederic.picasaviewer4android.modules;

import com.google.gdata.client.photos.PicasawebService;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import java.net.MalformedURLException;
import java.net.URL;

import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModelImpl;
import fr.frederic.picasaviewer4android.util.Constantes;
import fr.frederic.picasaviewer4android.util.PicasaHelper;

/**
 * Created by Frederic on 04/02/14.
 */
public class AlbumModule extends AbstractModule {

    @Override
    protected void configure() {
//        bind(URL.class).toProvider(urlProvider);
        bind(AlbumsModel.class).to(AlbumsModelImpl.class);

    }


//    final Provider<URL> urlProvider = new Provider<URL>(){
//        @Override
//        public URL get() {
//            try {
//                return new URL("https://picasaweb.google.com/data/feed/api/user/" + "frederic.minatchy" + "?kind=album");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//           return null;
//        }
//    };
}
