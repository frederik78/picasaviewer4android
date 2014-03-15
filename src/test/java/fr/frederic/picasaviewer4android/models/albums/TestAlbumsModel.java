package fr.frederic.picasaviewer4android.models.albums;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.frederic.picasaviewer4android.util.TechnicalException;
import fr.frederic.picasaviewer4android.vos.Album;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Frederic on 13/03/14.
 */

/**
 * Created by fminatchy on 25/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestAlbumsModel {

    AccountManager accountManager;

    Account account0;
    Account account1;
    Account account2;

    @Inject
    AlbumsModel albumsModel;

    @Mock
    PicasawebService picasawebService;

    @Before
    public void init() throws TechnicalException {
        MockitoAnnotations.initMocks(this);
        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(PicasawebService.class).toInstance(picasawebService);
                bind(AlbumsModel.class).to(AlbumsModelImpl.class);
            }
        };

        final Module roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        Modules.override(roboGuiceModule).with(module);

        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, roboGuiceModule, module);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembers(this);
    }



    @Test
    public void test_obtenir_albums() throws ServiceException, ExecutionException, InterruptedException, IOException, TechnicalException {

        final URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + "username" + "?kind=album");
        final UserFeed userFeed = mock(UserFeed.class);
        final List<GphotoEntry> entries = Arrays.asList(new GphotoEntry[]{spy(new GphotoEntry()), spy(new GphotoEntry())});
        when(userFeed.getEntries()).thenReturn(entries);
        when(picasawebService.getFeed(any(feedUrl.getClass()), Matchers.eq(UserFeed.class))).thenReturn(userFeed);
        when(userFeed.getEntries()).thenReturn(entries);
        final List<Album> albums = ((AlbumsModelImpl) albumsModel).getAlbumsFromPicasa("username");
        assertThat(albums).hasSize(2);
    }
}
