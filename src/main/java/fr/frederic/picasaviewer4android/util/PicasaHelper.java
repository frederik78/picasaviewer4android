package fr.frederic.picasaviewer4android.util;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.IFeed;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Frederic on 06/03/14.
 */
public class PicasaHelper {

    private PicasawebService picasawebService = new PicasawebService(Constantes.APPLICATION_NAME);

    public <F extends IFeed> F getFeed(URL feedUrl, Class<F> feedClass)
            throws IOException, ServiceException {
        return picasawebService.getFeed(feedUrl, feedClass);
    }

    public PicasawebService getPicasawebService() {
        return picasawebService;
    }
}
