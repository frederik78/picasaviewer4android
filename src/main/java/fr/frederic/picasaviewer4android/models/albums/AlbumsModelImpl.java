package fr.frederic.picasaviewer4android.models.albums;

import android.accounts.Account;
import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.inject.Singleton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import fr.frederic.picasaviewer4android.util.Constantes;
import fr.frederic.picasaviewer4android.util.TechnicalException;
import fr.frederic.picasaviewer4android.vos.Album;

/**
 * Created by Frederic on 03/02/14.
 */
@Singleton
public class AlbumsModelImpl extends AbstractAlbumsModel {


    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    private PicasawebService picasawebService;

    /**
     * Constructeur
     * @param username login utilisateur
     * @param password mot de passe utilisateur
     * @throws TechnicalException lorsqu'il y a une erreur d'authentification
     */
    public AlbumsModelImpl(String username, String password) throws TechnicalException {
        try {
            picasawebService = new PicasawebService(Constantes.APPLICATION_NAME);
            picasawebService.setUserCredentials(username, password);
        } catch (AuthenticationException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public List<Album> getAllAlbums(final String username) throws TechnicalException {

        final Callable<List<AlbumEntry>> callableAlbums = new Callable<List<AlbumEntry>>() {

            @Override
            public List<AlbumEntry> call() throws Exception {
                final URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + username + "?kind=album");

                final List<AlbumEntry> albums = new ArrayList<>();
                UserFeed userFeed = picasawebService.getFeed(feedUrl, UserFeed.class);
// Obtenir les galleries associées = userFeed.getEntries().get(1).getAuthors()
                List<GphotoEntry> entries = userFeed.getEntries();
                for (GphotoEntry entry : entries) {
                    final AlbumEntry ae = new AlbumEntry(entry);
//                    Album album = new Album(ae.getId(), ae.getName(), null);
                    albums.add(ae);
                }

                return albums;

            }
        };
        final Future<List<AlbumEntry>> futureAlbums = executor.submit(callableAlbums);

        try {
            List<AlbumEntry> result = futureAlbums.get();
        } catch (InterruptedException e) {
            throw new TechnicalException(e);
        } catch (ExecutionException e) {
            throw new TechnicalException(e);
        }

        executor.shutdown();
        return  new ArrayList<>();

    }
}
