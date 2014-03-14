package fr.frederic.picasaviewer4android.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.inject.Inject;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.lists.ListAlbumsAdapter;
import fr.frederic.picasaviewer4android.models.AlbumModelListener;
import fr.frederic.picasaviewer4android.models.albums.AlbumsModel;
import fr.frederic.picasaviewer4android.util.TechnicalException;
import fr.frederic.picasaviewer4android.vos.Album;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

import roboguice.activity.RoboListActivity;

public class AlbumsActivity extends RoboListActivity implements AlbumModelListener {

    @Inject
    private AlbumsModel albumsModel;

    private static final int DIALOG_ACCOUNTS = 0;

    private static final String PREF_ACCOUNT_NAME = "accountName";

    private static final int REQUEST_ACCOUNT_PICKER = 2;

    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;

    static final int REQUEST_CODE_PICK_ACCOUNT = 1002;


    private String token;

    private static final String GOOGLE_ANDROID = "com.android.email";

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();

    GoogleAccountCredential credential;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Google Accounts
        credential =
                GoogleAccountCredential.usingOAuth2(this, Collections.singleton("https://picasaweb.google.com/data/"));

        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
        credential.setSelectedAccountName(settings.getString(PREF_ACCOUNT_NAME, null));

        albumsModel.addListener(this);
        context = this;

    }

    @Override
    protected void onStart() {
        super.onStart();
//        try {
//            this.setListAdapter(new ListAlbumsAdapter(this, albumsModel.getAllAlbums("default")));
//        } catch (TechnicalException e) {
//            throw new RuntimeException("Impossible d'obtenir les albums", e);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (com.google.gdata.util.ServiceException e) {
//            e.printStackTrace();
//        }
        //        chooseAccount();
//        showAccountPicker();
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
    protected void onResume() {
        super.onResume();
        if (checkPlayServices()) {
            // Then we're good to go!
        }
    }

    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "This device is not supported.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this,
                REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }


        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICK_ACCOUNT:
                if (data != null && data.getExtras() != null) {
                    final String accountName  = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        credential.setSelectedAccountName(accountName);
                        SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.commit();
                        final String[] token = new String[1];
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    final String tokenStr = GoogleAuthUtil.getToken(context, accountName, "https://picasaweb.google.com/data/");
                                    GoogleCredential googleCredential = new GoogleCredential();
                                    token[0] = credential.getToken();
                                    Logger.getLogger(AlbumsActivity.class.getName()).info(token[0]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (GoogleAuthException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

//                        new EndpointsTask().execute(getApplicationContext());
                    }
                }
                break;
        }
    }

    private void showAccountPicker() {
        Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(
                null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                true, null, null, null, null);
        startActivityForResult(pickAccountIntent, REQUEST_CODE_PICK_ACCOUNT);
    }


    public void chooseAccount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
            }
        });
    }

    @Override
    public void updateData() throws TechnicalException {
        Toast.makeText(this, "Donnees mises à jour", 10);
//        try {
//            setListAdapter(new ListAlbumsAdapter(this, albumsModel.getAllAlbums("default")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (com.google.gdata.util.ServiceException e) {
//            e.printStackTrace();
//        }
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

//    private void gotAccount(final Account account) {
//        SharedPreferences settings = getSharedPreferences(PREF, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("accountName", account.name);
//        editor.commit();
//        new Thread() {
//
//            @Override
//            public void run() {
//
//            }
//        }.start();
//    }

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
