package com.soleeklab.www.countries;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CountriesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<countries>> {
    private static final int LOADER_ID = 1;
private CountriesAdapter adapter;
    private TextView mEmptyStateTextView;
    private static final String USGS_REQUEST_URL ="http://countryapi.gear.host/v1/Country/getCountries";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        ListView countriesListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        countriesListView.setEmptyView(mEmptyStateTextView);

        Button reloadButton=(Button)findViewById(R.id.reload_Button);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= 11){

                    recreate();

                }else{
                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                    recreate();
                }
            }
        });

        adapter = new CountriesAdapter(this, new ArrayList<countries>());
        countriesListView.setAdapter(adapter);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            reloadButton.setVisibility(View.GONE);

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
    }

    @Override
    public Loader<List<countries>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new countryLoader(this, USGS_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<countries>> loader, List<countries> country) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_countries);


        adapter.clear();


        if (country != null && !country.isEmpty()) {


            //to test setEmpty clear this line
           adapter.addAll(country);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<countries>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }


    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent logoutIntent=new Intent(CountriesActivity.this,LoginActivity.class);
        startActivity(logoutIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
