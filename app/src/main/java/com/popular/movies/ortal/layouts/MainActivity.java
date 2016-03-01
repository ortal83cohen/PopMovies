package com.popular.movies.ortal.layouts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.popular.movies.ortal.BuildConfig;
import com.popular.movies.ortal.R;
import com.popular.movies.ortal.client.Api;
import com.popular.movies.ortal.client.ApiConfig;
import com.popular.movies.ortal.client.DefaultHttpClient;
import com.popular.movies.ortal.client.RetrofitLogger;
import com.popular.movies.ortal.data.Movie;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements MovieViewHolder.Listener {
    private static final String FRAGMENT_LIST = "list";
    private OkHttpClient mHttpClient;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ApiConfig cfg = new ApiConfig();
        cfg.setDebug(BuildConfig.DEBUG);
        cfg.setLogger(new RetrofitLogger());
        mApi = new Api(cfg, apiHttpClient());
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ListFragment.newInstance(),
                            FRAGMENT_LIST)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onMovieClick(Movie movie, int position) {

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragmentDetails = fragmentManager.findFragmentById(R.id.fragment_container_details);
        if (fragmentDetails == null) {

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, DetailsFragment.newInstance(movie))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_details, DetailsFragment.newInstance(movie))
                    .commit();
        }
    }

    public Api getApi() {
        return mApi;
    }

    private OkHttpClient apiHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient(this);

            mHttpClient.setConnectTimeout(999999, TimeUnit.MILLISECONDS);
            mHttpClient.setReadTimeout(999999, TimeUnit.MILLISECONDS);
            mHttpClient.interceptors().add(RetrofitLogger.create());
        }
        return mHttpClient;
    }
}
