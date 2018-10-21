package com.soleeklab.www.countries;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by mohamed on 10/19/2018.
 */

public class countryLoader extends AsyncTaskLoader<List<countries>> {
    private static final String LOG_TAG = countryLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public countryLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<countries> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<countries> country = QueryUtils.fetchCountryData(mUrl);
        return country;
    }
}
