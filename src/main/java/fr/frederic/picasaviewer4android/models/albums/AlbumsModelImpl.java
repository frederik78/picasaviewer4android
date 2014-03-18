package fr.frederic.picasaviewer4android.models.albums;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.frederic.picasaviewer4android.util.TechnicalException;
import fr.frederic.picasaviewer4android.vos.Album;

/**
 * Created by Frederic on 03/02/14.
 */
@Singleton
public class AlbumsModelImpl extends AbstractAlbumsModel {


    private static ExecutorService executor = Executors.newFixedThreadPool(2);


    @Inject
    private PicasawebService picasawebService;

    /**
     * Constructeur
     *
     * @param username login utilisateur
     * @param password mot de passe utilisateur
     * @throws TechnicalException lorsqu'il y a une erreur d'authentification
     */
    public void setCredential(final String username, final String password) throws TechnicalException {
        if (picasawebService != null) {
            final Callable<Void> callable = new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    try {
                        picasawebService.setUserCredentials(username, password);
                        return null;
                    } catch (AuthenticationException e) {
                        throw new TechnicalException(e);
                    }
                }
            };
            Future<Void> future = executor.submit(callable);
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new TechnicalException(e);
            } catch (ExecutionException e) {
                throw new TechnicalException(e);
            }
        }
    }

    @Override
    public List<Album> getAllAlbums(final String username) throws TechnicalException {
        final Callable<List<Album>> callableAlbums = new Callable<List<Album>>() {
            @Override
            public List<Album> call() throws Exception {
                return getAlbumsFromPicasa(username);
            }
        };
        final Future<List<Album>> futureAlbums = executor.submit(callableAlbums);
        List<Album> albumEntries = Collections.emptyList();
        try {
            albumEntries = futureAlbums.get();
        } catch (InterruptedException e) {
            throw new TechnicalException(e);
        } catch (ExecutionException e) {
            throw new TechnicalException(e);
        }

        executor.shutdown();
        return albumEntries;

    }

    /**
     * Retoure une liste d'albums en provenance de Picasa
     *
     * @param username nom de l'utilisateur
     * @return une liste d'albums
     * @throws TechnicalException en cas d'erreurs
     */
    protected List<Album> getAlbumsFromPicasa(String username) throws TechnicalException {
        final List<Album> albums = new LinkedList<>();
        try {
            final URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + username + "?kind=album");
            final UserFeed userFeed = picasawebService.getFeed(feedUrl, UserFeed.class);
            // Obtenir les galleries associées = userFeed.getEntries().get(1).getAuthors()
            List<GphotoEntry> entries = userFeed.getEntries();
            for (GphotoEntry entry : entries) {
                final AlbumEntry ae = new AlbumEntry(entry);
                ae.getMediaContents().get(0).getUrl();
                Album album = new Album(ae.getId(), ae.getName(), null);
                albums.add(album);
            }
        } catch (IOException e) {
            throw new TechnicalException(e);
        } catch (ServiceException e) {
            throw new TechnicalException(e);
        }
        return albums;
    }
}
