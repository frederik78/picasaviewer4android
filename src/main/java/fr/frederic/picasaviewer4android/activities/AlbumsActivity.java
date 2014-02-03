package fr.frederic.picasaviewer4android.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Toast;

import fr.frederic.picasaviewer4android.R;
import fr.frederic.picasaviewer4android.lists.ListAlbumsAdapter;
import fr.frederic.picasaviewer4android.models.AlbumModel;
import fr.frederic.picasaviewer4android.models.AlbumModelImpl;
import fr.frederic.picasaviewer4android.models.AlbumModelListener;

public class AlbumsActivity extends ListActivity implements AlbumModelListener {


    private AlbumModel albumModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        albumModel.addListener(this);
        albumModel = new AlbumModelImpl();
        this.setListAdapter(new ListAlbumsAdapter(this, albumModel.getAllAlbums()));
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
        ((BaseAdapter) this.getListAdapter()).notifyDataSetChanged();
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
