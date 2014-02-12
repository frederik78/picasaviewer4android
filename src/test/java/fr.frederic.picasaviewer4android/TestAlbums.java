package fr.frederic.picasaviewer4android;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ListView;

import com.google.inject.Module;
import com.google.inject.util.Modules;

import fr.frederic.picasaviewer4android.activities.AlbumsActivity;
import fr.frederic.picasaviewer4android.activities.PicturesActivity;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModelImpl;
import fr.frederic.picasaviewer4android.modules.AlbumModule;
import fr.frederic.picasaviewer4android.modules.TestAlbumModule;
import fr.frederic.picasaviewer4android.vos.Album;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import roboguice.inject.RoboInjector;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Frederic on 04/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestAlbums {

    private AlbumsActivity activity;

    @InjectResource(R.drawable.ic_launcher)
    private static Drawable image;

    @Spy
    private final AlbumsModel albumsModel = spy(new AlbumsModelImpl());


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final Module roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        final Module albumModule = Modules.override(roboGuiceModule).with(new AlbumModule());
        final Module testModule = Modules.override(albumModule).with(new TestAlbumModule(albumsModel));

        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, testModule);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembersWithoutViews(this);
    }

    @Test
    public void nombre_elements_dans_adapter() {

        when(albumsModel.getAllAlbums()).thenReturn(create50Albums());
        activity = Robolectric.buildActivity(AlbumsActivity.class)
                .create().get();
        assertThat(activity.getListAdapter().getCount()).isEqualTo(50);
    }

    @Test
    public void contenu_liste_albums() {
        final List<Album> albums = create50Albums();
        when(albumsModel.getAllAlbums()).thenReturn(albums);
        activity = Robolectric.buildActivity(AlbumsActivity.class)
                .create().get();

        assertThat(activity.getListView().getItemAtPosition(25)).isEqualTo(albums.get(25));
        assertThat(activity.getListView().getItemAtPosition(25)).isNotEqualTo(albums.get(26));
    }

    @Test
    public void changement_contenu_suite_a_changement_donnees() {

        final List<Album> albums = create50Albums();
        when(albumsModel.getAllAlbums()).thenReturn(albums);
        activity = Robolectric.buildActivity(AlbumsActivity.class)
                .create().get();
        assertThat(activity.getListView().getItemAtPosition(25)).isEqualTo(albums.get(25));
        assertThat(activity.getListView().getItemAtPosition(25)).isNotEqualTo(albums.get(26));

        final List<Album> albums1 = createAnother50Albums();
        when(albumsModel.getAllAlbums()).thenReturn(albums1);
        albumsModel.notifyUpdate();
        assertThat(activity.getListView().getItemAtPosition(25)).isEqualTo(albums1.get(25));
        assertThat(activity.getListView().getItemAtPosition(25)).isNotEqualTo(albums1.get(26));
    }

    @Test
    public void verification_picture_activity_appelee() {
        activity = Robolectric.buildActivity(AlbumsActivity.class)
                .create().get();
        final ListView listView = (ListView) activity.findViewById(android.R.id.list);
        listView.performItemClick(listView.getAdapter().getView(0, null, null),
                0, listView.getAdapter().getItemId(0));
        final Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertThat(PicturesActivity.class.getCanonicalName()).isEqualTo(intent.getComponent().getClassName());

    }

    /**
     * Création de 50 albums
     *
     * @return une collection d'albums
     */
    private static List<Album> create50Albums() {
        final List<Album> albums = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            albums.add(new Album(i, "Album_" + i, image));
        }
        return albums;
    }


    /**
     * Création de 50 autres albums
     *
     * @return une nouvelle collection d'albums
     */
    private static List<Album> createAnother50Albums() {
        final List<Album> albums = new ArrayList<>(50);
        for (int i = 50; i < 100; i++) {
            albums.add(new Album(i, "Album_" + i, image));
        }
        return albums;
    }


}
