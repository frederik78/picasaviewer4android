package fr.frederic.picasaviewer4android.vos;

import android.graphics.drawable.Drawable;

/**
 * Created by Frederic on 02/02/14.
 */
public class Album
{
   private long id;

   private String albumName;

   private Drawable image;

   public Album(long id, String albumName, Drawable image)
   {
      this.id = id;
      
      this.albumName = albumName;

      this.image = image;
   }


   public long getId()
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
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (!(o instanceof Album))
      {
         return false;
      }

      Album album = (Album) o;

      if (id != album.id)
      {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (int) (id ^ (id >>> 32));
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
