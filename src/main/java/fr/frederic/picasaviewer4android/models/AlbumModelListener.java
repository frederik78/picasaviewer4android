package fr.frederic.picasaviewer4android.models;

import fr.frederic.picasaviewer4android.util.TechnicalException;

/**
 * Created by Frederic on 04/02/14.
 */
public interface AlbumModelListener {
    /**
     * Met à jour les informations
     */
    void updateData() throws TechnicalException;
}
