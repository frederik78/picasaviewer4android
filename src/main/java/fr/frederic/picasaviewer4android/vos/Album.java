package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Frederic on 02/02/14.
 */
public class Album implements Serializable
{
   private String id;

   private String albumName;

   private transient Drawable image;

   public Album(String id, String albumName, Drawable image)
   {
      this.id = id;

      this.albumName = albumName;

      this.image = image;
   }


   public String getId()
   {
      return id;
   }

   public String getAlbumName()
   {
      return albumName;
   }

   public Drawable getImage()
   {
      return image;
   }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;

        Album album = (Album) o;

        if (!id.equals(album.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
   public String toString()
   {
      return "Album{" +
            "id=" + id +
            ", albumName='" + albumName + '\'' +
            ", image=" + image +
            '}';
   }
}
