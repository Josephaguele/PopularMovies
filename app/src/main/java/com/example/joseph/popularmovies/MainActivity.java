package com.example.joseph.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private static String QUERY_URL = "https://api.themoviedb.org/3/movie/popular?api_key=c20c1695d76d341c16a929a587a97dfb";
    private TheAdapter adapter;

    TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity); // setContent to the grid view layout


        // Get the list of movies from QueryUtils
        GridView gridView = (GridView) findViewById(R.id.listview);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty);
        gridView.setEmptyView(mEmptyStateTextView);
        // create a new adapter that takes in a list of earthquakes
        adapter = new TheAdapter(this, new ArrayList<Movies>());

        // set the adapter on the GridView so the list can be populated in the user interface
        gridView.setAdapter(adapter);
        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();

        // this onclick method opens any movie clicked to launch the detail of that movie.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // calling intent to launch detail activity
                Movies currentMoviePoster = adapter.getItem(position);
                Movies movies = new Movies();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("Movies", currentMoviePoster);
                startActivity(intent);


            }
        });

        // Get a reference to the ConnectivityMgr to check the state of the network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

       if (networkInfo != null ) {
            // get a reference to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager1 = getLoaderManager();
           View loadingIndicator = findViewById(R.id.progress);
           loadingIndicator.setVisibility(View.GONE);

            // Initalize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in the activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface)
            loaderManager1.initLoader(1, null, this);

       } else {
           View loadingIndicator = findViewById(R.id.progress);
           loadingIndicator.setVisibility(View.GONE);
           mEmptyStateTextView.setText("no internet connection");

           return;

            // UPDATE empty state with no connection error message
        }

    }

    @NonNull
    @Override
    public Loader<List<Movies>>  onCreateLoader(int i, @Nullable Bundle bundle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         String popular = sharedPreferences.getString
                (getString(R.string.settings_popular_key), "");

        Uri baseUri = Uri.parse(QUERY_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //uriBuilder.appendQueryParameter("sortby", popular);
        return new MoviesLoader(this, uriBuilder.toString());


    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movies>> loader, List<Movies> movies) {
        // Update the UI with the result
        // sames as onPostExecute method of the AsyncTask

        // clear the adapter of previous data
        adapter.clear();

        // if the movie list is valid, then add to the list and update it
        if (movies != null && !movies.isEmpty()) {
            adapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> movies) {
        // Loader reset, so we can clear out our existing data
        adapter.setMovies(new ArrayList<Movies>());
        adapter.clear();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    // This method is called when you select an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
        }
        return true;
    }

}
