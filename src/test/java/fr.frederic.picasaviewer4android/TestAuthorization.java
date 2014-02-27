package fr.frederic.picasaviewer4android;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import fr.frederic.picasaviewer4android.activities.AlbumsActivity;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by fminatchy on 25/02/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestAuthorization {

    AccountManager accountManager;

    Account account0;
    Account account1;
    Account account2;

    @Before
    public void init() {
        creationComptes();
        accountManager = AccountManager.get(Robolectric.application);
        shadowOf(accountManager).addAccount(account0);
        shadowOf(accountManager).addAccount(account1);
        shadowOf(accountManager).addAccount(account2);
    }

    @Test
    public void test_comptes() {
       final AlbumsActivity activity = Robolectric.buildActivity(AlbumsActivity.class).create().get();

       final Account[] accounts = activity.getGoogleAccounts();
       assertThat(accounts).containsExactly(account0, account2);


    }

    private void creationComptes() {
        account0 = new Account("compte n°1", GoogleAccountManager.ACCOUNT_TYPE);
        account1 = new Account("compte n°2", "pas google");
        account2 = new Account("compte n°3", GoogleAccountManager.ACCOUNT_TYPE);
    }

}
