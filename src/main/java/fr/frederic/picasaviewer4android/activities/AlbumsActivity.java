package fr.frederic.picasaviewer4android.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.inject.Inject;

import java.util.Collections;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.lists.ListAlbumsAdapter;
import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.vos.Album;
import roboguice.activity.RoboListActivity;

public class AlbumsActivity extends RoboListActivity implements AlbumModelListener {

    @Inject
    private AlbumsModel albumsModel;

    private static final int DIALOG_ACCOUNTS = 0;

    private static final String PREF = "MyPrefs";

    private static final String GOOGLE_ANDROID = "com.android.email";
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();

    GoogleAccountCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Accounts
        credential =
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton("https://picasaweb.google.com/data/"));

        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
//        credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));


        albumsModel.addListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setListAdapter(new ListAlbumsAdapter(this, albumsModel.getAllAlbums()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        final Album album = albumsModel.getAlbum(id);
        final Intent intent = new Intent(this, PicturesActivity.class);
        intent.putExtra("album", album);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateData() {
        Toast.makeText(this, "Donnees mises à jour", 10);
        setListAdapter(new ListAlbumsAdapter(this, albumsModel.getAllAlbums()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((BaseAdapter) getListAdapter()).notifyDataSetChanged();
            }
        });

    }


//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DIALOG_ACCOUNTS:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Select a Google account");
//                final Account[] accounts = getGoogleAccounts();
//                builder.setItems(accountsNames, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        gotAccount(GoogleAccountManager., accountsNames[which]);
//                    }
//                });
//                return builder.create();
//        }
//        return null;
//    }

    private void gotAccount(final Account account) {
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accountName", account.name);
        editor.commit();
        new Thread() {

            @Override
            public void run() {

            }
        }.start();
    }

    /**
     * Recherche les compte google présents
     *
     * @return les comptes Google
     */
    public Account[] getGoogleAccounts() {
        final AccountManager accountManager = AccountManager.get(this.getApplicationContext());
        return accountManager.getAccountsByType(GoogleAccountManager.ACCOUNT_TYPE);
    }

    public void initHttpTransport() {

        GoogleCredential credential = new GoogleCredential();
        HttpTransport httpTransport = credential.getTransport();
//        final Account[] accounts = accountManager.getAccounts();

        SharedPreferences settings = getSharedPreferences(PREF, 0);

//        HttpTransport httpTransport = new NetHttpTransport();
//        JacksonFactory jsonFactory = new JacksonFactory();
//        GoogleCredential credential = new GoogleCredential.Builder()
//                .setTransport(httpTransport)
//                .setJsonFactory(jsonFactory)
//                .setClientSecrets(
//                        new GoogleClientSecrets().setInstalled(
//                                new GoogleClientSecrets.Details()
//                                        .setAuthUri(AUTH_URI)
//                                        .setClientId(CLIENT_ID)
//                                                //.setClientSecret("but I haven't got one!")
//                                        .setRedirectUris(REDIRECT_URIS)
//                        )
//                )
//                .build()
//                .setAccessToken(ACCESS_TOKEN) //defined above
//                .setRefreshToken(REFRESH_TOKEN); //defined above


//        transport = new HttpTransport();

        //        GoogleHeaders headers = (GoogleHeaders) transport.defaultHeaders;
//        headers.setApplicationName("Google-PicasaAndroidAample/1.0");
//        headers.gdataVersion = "2";
//        AtomParser parser = new AtomParser();
//        parser.namespaceDictionary = Util.NAMESPACE_DICTIONARY;
//        transport.addParser(parser);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
