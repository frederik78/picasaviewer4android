package fr.frederic.picasaviewer4android.activities;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import fr.frederic.picasaviewer4android.AbstractTest;
import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.activities.ImageActivity;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModel;
import fr.frederic.picasaviewer4android.models.pictures.PicturesModelImpl;
import fr.frederic.picasaviewer4android.modules.PictureModule;
import fr.frederic.picasaviewer4android.modules.TestPictureModule;
import fr.frederic.picasaviewer4android.vos.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import roboguice.inject.InjectResource;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Frederic on 16/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestImages extends AbstractTest {

    private ImageActivity activity;

    @InjectResource(R.drawable.ic_launcher)
    private static Drawable image;

    @Spy
    private final PicturesModel picturesModel = spy(new PicturesModelImpl());

    /**
     * Last touched view
     */
    private View lastTouchView;

    /**
     * Last touched X coordinate
     */
    private float lastTouchX;
    /**
     * Last touched Y coordinate
     */
    private float lastTouchY;

    @Override
    public Module addCustomeModules() {
        final Module pictureModule = Modules.override(roboGuiceModule).with(new PictureModule());
        return Modules.override(pictureModule).with(new TestPictureModule(picturesModel));

    }

    @Test
    public void scroll_image() {
        final Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), ImageActivity.class);
        final Album album = new Album(Integer.toString(1), "album_1", image);
        intent.putExtra("album", album);
        intent.putExtra("position", 2);
        intent.putExtra("imageId", 2L);

        // Get the activity
        activity = Robolectric.buildActivity(ImageActivity.class).withIntent(intent).create().get();
        final Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);

        // Get a spy viewer otherwise viewPager's width is always 0
        final ViewPager viewPager = spy((ViewPager) activity.findViewById(R.id.viewPager));
        viewPager.setVisibility(View.VISIBLE);
        when(viewPager.getWidth()).thenReturn(point.x - 50);

        // First item sent by viewPager before swipe
        final int firstItem = viewPager.getCurrentItem();

        // Swipe
        drag(viewPager, 10F, 10F, point.x - 60F, 10F);

        // Next item after swipe
        final int secondItem = viewPager.getCurrentItem();

        // Comparison
        assertThat(firstItem).isEqualTo(2);
        assertThat(secondItem).isEqualTo(1);
    }

    public void touchDown(View view, float x, float y) {
        lastTouchX = x;
        lastTouchY = y;
        lastTouchView = view;

        sendMotionEvent(view, MotionEvent.ACTION_DOWN, x, y);
    }

    public void touchMove(float x, float y) {
        lastTouchX = x;
        lastTouchY = y;

        sendMotionEvent(lastTouchView, MotionEvent.ACTION_MOVE, x, y);
    }

    public void touchUp() {
        sendMotionEvent(
                lastTouchView, MotionEvent.ACTION_UP, lastTouchX, lastTouchY);

        lastTouchView = null;
    }

    public void drag(View view, float xStart, float yStart,
                     float xEnd, float yEnd) {
        touchDown(view, xStart, yStart);
        touchMove(xEnd, yEnd);
        touchUp();
    }

    private void sendMotionEvent(View view, int action, float x, float y) {

        int[] screenOffset = new int[2];
        view.getLocationOnScreen(screenOffset);

        MotionEvent event = MotionEvent.obtain(100, 200, action,
                x + screenOffset[0], y + screenOffset[1], 0);

        shadowOf(event).setPointerIds(1, 2);
        shadowOf(event).setPointerIndex(1);

        view.onTouchEvent(event);
        view.dispatchTouchEvent(event);
    }
}
