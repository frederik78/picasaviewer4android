package fr.frederic.picasaviewer4android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.ServiceException;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.util.Modules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModelImpl;
import fr.frederic.picasaviewer4android.modules.AlbumModule;
import fr.frederic.picasaviewer4android.util.PicasaHelper;
import fr.frederic.picasaviewer4android.util.TechnicalException;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by fminatchy on 25/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestAuthorization extends fr.frederic.picasaviewer4android.AbstractTest {

    AccountManager accountManager;

    Account account0;
    Account account1;
    Account account2;

    @Inject
    AlbumsModel albumsModel;

    @Override
    public Module addCustomeModules() {
        return Modules.override(roboGuiceModule).with(new AlbumModule());
    }

    @Override
    protected void customizeSetup() throws TechnicalException {
        creationComptes();
        accountManager = AccountManager.get(Robolectric.application);
        shadowOf(accountManager).addAccount(account0);
        shadowOf(accountManager).addAccount(account1);
        shadowOf(accountManager).addAccount(account2);
    }

    @Test
    public void test_comptes() throws IOException, ServiceException, TechnicalException {
//       final AlbumsActivity activity = Robolectric.buildActivity(AlbumsActivity.class).create().get();
//
//       final Account[] accounts = activity.getGoogleAccounts();
//       assertThat(accounts).containsExactly(account0, account2);

        final URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/" + "username" + "?kind=album");
        final List<AlbumEntry> albums = new ArrayList<>();
        UserFeed userFeed = mock(UserFeed.class);
        GphotoEntry gphotoEntry1 = mock(GphotoEntry.class);
        GphotoEntry gphotoEntry2 = mock(GphotoEntry.class);
        List<GphotoEntry> entries = Arrays.asList(new GphotoEntry[] {gphotoEntry1, gphotoEntry2});
        when(userFeed.getEntries()).thenReturn(entries);
        final PicasawebService picasawebService = mock(PicasawebService.class);

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(PicasawebService.class).toInstance(picasawebService);
                bind(AlbumsModel.class).to(AlbumsModelImpl.class);
            }
        };

        roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        Modules.override(roboGuiceModule).with(module);

        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, roboGuiceModule, module);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembers(this);
        when(picasawebService.getFeed(any(feedUrl.getClass()), Matchers.eq(UserFeed.class))).thenReturn(userFeed);

        albumsModel.getAllAlbums("pipo");
    }

    @Test
    public void test_obtenir_albums() throws ServiceException, ExecutionException, InterruptedException, IOException, TechnicalException {

//        final AlbumsModel albumsModel = new AlbumsModelImpl("","");
//        List<Album> albumList = albumsModel.getAllAlbums("default");


    }


    private void creationComptes() {
        account0 = new Account("compte n°1", GoogleAccountManager.ACCOUNT_TYPE);
        account1 = new Account("compte n°2", "pas google");
        account2 = new Account("compte n°3", GoogleAccountManager.ACCOUNT_TYPE);
    }

}
