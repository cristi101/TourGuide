package eu.baboi.cristian.tourguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import eu.baboi.cristian.tourguide.adapters.PlacesAdapter;
import eu.baboi.cristian.tourguide.adapters.viewholders.PlacesViewHolder;
import eu.baboi.cristian.tourguide.fragments.CategoryAdapter;
import eu.baboi.cristian.tourguide.fragments.PlacesFragment;
import eu.baboi.cristian.tourguide.utils.PagingCallbacks;
import eu.baboi.cristian.tourguide.utils.SettingsActivity;
import eu.baboi.cristian.tourguide.utils.net.Loaders;
import eu.baboi.cristian.tourguide.utils.net.model.Model;
import eu.baboi.cristian.tourguide.utils.net.requests.NearbySearchRequest;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearch;
import eu.baboi.cristian.tourguide.utils.net.results.PlacesSearchResult;
import eu.baboi.cristian.tourguide.utils.secret.DataStore;

public class MainActivity extends AppCompatActivity implements PlacesFragment.IPlaces, PagingCallbacks.Progress, DataStore.Listener {
    private static final String LOG = MainActivity.class.getName();

    private AlertDialog dialog = null;
    private DataStore dataStore = null;

    private ProgressBar progress;
    private PlacesAdapter[] adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);

        ViewPager viewPager = findViewById(R.id.view_pager);
        CategoryAdapter adapter = new CategoryAdapter(getSupportFragmentManager(), getApplicationContext());
        adapters = new PlacesAdapter[adapter.getCount()];
        viewPager.setAdapter(adapter);

        prepareLoading();
    }

    private void setupActionBar() {
        ActionBar bar = getSupportActionBar();
        if (bar == null) return;

        bar.setCustomView(R.layout.logo);
        bar.setDisplayShowCustomEnabled(true);

        View customView = bar.getCustomView();
        TextView title = customView.findViewById(R.id.title);
        title.setText(bar.getTitle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataStore.unregisterChangeListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dialog != null) {
            dialog.dismiss();//prevent leak
            dialog = null;
        }
    }

    // ask for password then init screen
    private void prepareLoading() {
        dataStore = new DataStore(this, Model.PASSWORD);
        dataStore.registerChangeListener(this);

        String password = dataStore.getString(Model.PASSWORD_KEY, null);
        if (TextUtils.isEmpty(password)) showPasswordDialog();
    }

    //load data into the adapter if not already loaded
    private void initLoading(PlacesAdapter adapter) {
        PagingCallbacks<PlacesSearchResult, PlacesSearch, NearbySearchRequest,
                NearbySearchRequest.Response, PlacesViewHolder, PlacesAdapter>
                callbacks = new PagingCallbacks<>(adapter, adapter.request());
        Loaders.initLoader(this, adapter.id(), null, callbacks);
    }

    //force loading data into the adapter
    private void startLoading(PlacesAdapter adapter) {
        PagingCallbacks<PlacesSearchResult, PlacesSearch, NearbySearchRequest,
                NearbySearchRequest.Response, PlacesViewHolder, PlacesAdapter>
                callbacks = new PagingCallbacks<>(adapter, adapter.request());
        Loaders.startLoader(this, adapter.id(), null, callbacks);
    }

    //load data into all adapters
    private void load() {
        if (adapters == null || adapters.length == 0) return;
        for (PlacesAdapter adapter : adapters) {
            if (adapter == null) continue;
            if (adapter.request() == null) continue;

            startLoading(adapter);//force load
        }
    }

    // ask for the password, save the password
    private void showPasswordDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setView(dialogView);
        builder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dlg, int which) {
                if (dialog != null) {
                    EditText text = dialog.findViewById(R.id.password);
                    String password = text.getText().toString().trim();

                    dialog.dismiss();
                    dialog = null;

                    if (TextUtils.isEmpty(password)) showPasswordDialog();
                    else if (dataStore != null)
                        //save the password & call load()
                        dataStore.putString(Model.PASSWORD_KEY, password);
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    //  menu handling
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setPlacesAdapter(PlacesAdapter adapter) {
        if (adapters != null) {
            adapters[adapter.id()] = adapter;
            initLoading(adapter);
        }
    }


    @Override
    public ProgressBar getProgressBar() {
        return progress;
    }

    @Override
    public void onSharedPreferenceChanged(DataStore dataStore, String key) {
        if (!key.equals(Model.PASSWORD_KEY)) return;
        load();
    }
}
