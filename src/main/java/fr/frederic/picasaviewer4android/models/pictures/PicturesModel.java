package fr.frederic.picasaviewer4android.models.pictures;

import java.util.List;

import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.models.PictureModelListener;
import fr.frederic.picasaviewer4android.vos.Album;
import fr.frederic.picasaviewer4android.vos.Picture;

/**
 * Created by Frederic on 08/02/14.
 */
public interface PicturesModel {

    /**
     * Nombre de photos dans l'album
     *
     * @param album compte les photos de l'album
     * @return le nombre de photos dans l'album
     */
    int getCount(Album album);

    /**
     * Retourne la liste de toutes les photos
     *
     * @param album dont l'ont souhaite visualiser les photos
     * @return une liste de toutes les photos de l'album
     */
    List<Picture> getAllPictures(Album album);

    /**
     * Retourne une photo
     *
     * @param id de la photo
     * @return une photo
     */
    Picture getPicture(long id);

    /**
     * Indique les changements de données
     */
    void notifyUpdate();

    /**
     * Ajoute un écouteur
     *
     * @param pictureModelListener écouteur
     */
    void addListener(PictureModelListener pictureModelListener);


}
