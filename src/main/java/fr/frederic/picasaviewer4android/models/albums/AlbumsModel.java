package fr.frederic.picasaviewer4android.models.albums;

import java.util.List;

import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.vos.Album;

/**
 * Created by Frederic on 03/02/14.
 */
public interface AlbumsModel {

    /**
     * Nombre d'albums
     * @return le nombre d'albums
     */
    int getCount();

    /**
     * Retourne la liste des albums
     * @return une liste d'albums
     */
    List<Album> getAllAlbums();

    /**
     * Retourne un album
     * @param id de l'album
     * @return un album
     */
    Album getAlbum(long id);

    /**
     * Indique les changements de données
     */
    void notifyUpdate();

    /**
     * Ajoute un écouteur
     * @param albumModelListener écouteur
     */
    void addListener(AlbumModelListener albumModelListener);
}
