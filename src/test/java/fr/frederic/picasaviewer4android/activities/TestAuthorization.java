package fr.frederic.picasaviewer4android.activities;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.gdata.util.ServiceException;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import fr.frederic.picasaviewer4android.AbstractTest;
import fr.frederic.picasaviewer4android.modules.AlbumModule;
import fr.frederic.picasaviewer4android.util.TechnicalException;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Frederic on 13/03/14.
 */

/**
 * Created by fminatchy on 25/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestAuthorization extends AbstractTest {

    AccountManager accountManager;

    Account account0;
    Account account1;
    Account account2;

    protected void initAccounts() throws TechnicalException {
        creationComptes();
        accountManager = AccountManager.get(Robolectric.application);
        shadowOf(accountManager).addAccount(account0);
        shadowOf(accountManager).addAccount(account1);
        shadowOf(accountManager).addAccount(account2);
    }

    @Test
    public void test_comptes() throws IOException, ServiceException, TechnicalException {
        initAccounts();
        final AlbumsActivity activity = Robolectric.buildActivity(AlbumsActivity.class).create().get();
        final Account[] accounts = activity.getGoogleAccounts();
        assertThat(accounts).containsExactly(account0, account2);

    }

    private void creationComptes() {
        account0 = new Account("compte n°1", GoogleAccountManager.ACCOUNT_TYPE);
        account1 = new Account("compte n°2", "pas google");
        account2 = new Account("compte n°3", GoogleAccountManager.ACCOUNT_TYPE);
    }

    @Override
    public Module addCustomeModules() {
        return Modules.override(roboGuiceModule).with(new AlbumModule());
    }
}
