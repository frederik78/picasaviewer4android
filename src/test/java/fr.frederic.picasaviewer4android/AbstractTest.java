package fr.frederic.picasaviewer4android;

import com.google.inject.Module;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by Frederic on 22/02/14.
 */
public abstract class AbstractTest {

    protected Module roboGuiceModule;

    protected void init()
    {
        MockitoAnnotations.initMocks(this);

        roboGuiceModule = RoboGuice.newDefaultRoboModule(Robolectric.application);
        final Module testModule = addCustomeModules();

        RoboGuice.setBaseApplicationInjector(Robolectric.application, RoboGuice.DEFAULT_STAGE, testModule);
        RoboInjector injector = RoboGuice.getInjector(Robolectric.application);
        injector.injectMembersWithoutViews(this);
    }

    public abstract Module addCustomeModules();

    @Before
    public void setup() {
        init();
    }
}
