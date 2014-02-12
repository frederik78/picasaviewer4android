package fr.frederic.picasaviewer4android;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;

import com.google.inject.Module;
import com.google.inject.util.Modules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.frederic.picasaviewer4android.activities.PicturesActivity;
import fr.frederic.picasaviewer4android.models.pictures.PictureModelImpl;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;
import fr.frederic.picasaviewer4android.modules.PictureModule;
import fr.frederic.picasaviewer4android.modules.TestPictureModule;
import fr.frederic.picasaviewer4android.vos.Album;
import fr.frederic.picasaviewer4android.vos.Picture;
import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import roboguice.inject.RoboInjector;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Frederic on 09/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestPictures {

    private PicturesActivity picturesActivity;

    @InjectResource(R.drawable.ic_launcher)
    private static Drawable image;

    @Spy
    private final PicturesModel picturesModel = spy(new PictureModelImpl());


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        final Module roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        final Module pictureModule = Modules.override(roboGuiceModule).with(new PictureModule());
        final Module testModule = Modules.override(pictureModule).with(new TestPictureModule(picturesModel));

        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, testModule);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembersWithoutViews(this);
    }

    @Test
    public void rechercher_images(){

        final List<Picture> pictures = create50Pictures();
        final Album album = new Album(1,"album_1", image);
        when(picturesModel.getAllPictures(album)).thenReturn(pictures);
        final Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), PicturesActivity.class);
        intent.putExtra("album", album);
        picturesActivity = Robolectric.buildActivity(PicturesActivity.class).withIntent(intent).create().get();
        final GridView gridView = (GridView) picturesActivity.findViewById(R.id.gridview);
        assertThat(gridView.getCount()).isEqualTo(pictures.size());
    }

    /**
     * Création de 50 images
     *
     * @return une collection d'images
     */
    private static List<Picture> create50Pictures() {
        final List<Picture> pictures = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            pictures.add(new Picture(i, image));
        }
        return pictures;
    }

}
