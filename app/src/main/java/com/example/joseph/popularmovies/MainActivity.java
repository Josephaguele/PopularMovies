package com.example.joseph.popularmovies;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
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

/// The interface OnSharedPreferenceChangeListener is used to reflect the value of what was clicked
// in the listPreference. Any change in there is reflected in the mainActivity by the OnSharedPrefernceChangeListener
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movies>>,SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = MainActivity.class.getName();
    String url;

    /** URL for api movies data from the API movie dataset**/
    private static String QUERY_URL = "https://api.themoviedb.org/3/movie/popular?api_key=c20c1695d76d341c16a929a587a97dfb";

    // TextView that is displayed when the list is empty
    TextView mEmptyStateTextView;

    // Adapter for the list of movies
    private TheAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity); // setContent to the grid view layout

        // Get the list of movies from QueryUtils
        GridView gridView = (GridView) findViewById(R.id.listview);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty);
        gridView.setEmptyView(mEmptyStateTextView);

        // create a new adapter that takes in a list of earthquakes as input
        adapter = new TheAdapter(this, new ArrayList<Movies>());

        // set the adapter on the GridView so the list can be populated in the user interface
        gridView.setAdapter(adapter);

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // ANd register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();

        // this onclick method opens any movie clicked to launch the detail of that movie.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // calling intent to launch detail activity
                Movies currentMoviePoster = adapter.getItem(position);

                // calling the constructor of the movies class
                Movies movies = new Movies();

                // Create a new intent to launch the detailActivity
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                // tell the intent to carry the details of the currentMovie
                intent.putExtra("Movies", currentMoviePoster);

                // send the intent to launch the detailActivity
                startActivity(intent);

            }
        });

        // Get a reference to the ConnectivityMgr to check the state of the network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // IF there's a network connection, fetch data
        if (networkInfo != null) {
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

            // UPDATE empty state with no connection error message
            mEmptyStateTextView.setText("no internet connection");

            return;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key){

          adapter.clear();
          // emptyStateView disappears once sharedPreferences are loaded with network
          mEmptyStateTextView.setVisibility(View.GONE);

          View loadingIndicator  = findViewById(R.id.progress);
          loadingIndicator.setVisibility(View.VISIBLE);

          // Restart the loader to query the URL as the query settings have been updated
          getLoaderManager().restartLoader(1,null, this);

    }

    @NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int i, @Nullable Bundle bundle) {
        // This uses the PreferenceManager to get the default sharedPreferences as chosen in the list
        // preference by the user
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        //This code is what does the magic of sorting the movies by the item clicked in the list preference
        //i.e by popular, top_rated or upcoming. This code is what delayed me for two weeks extra before
        // submitting this application for review.

        // THE VALUE ARE WHAT DETERMINE THE OUTCOME OF THE QUERY.

        //EditTextPreference
        // moviePage is gotten from what is keyed in as the value by the user.
        // The value is what determines the outcome of what shows up
      String moviePage = sharedPref.getString(
              getString(R.string.pref_page_key),
              getString(R.string.pref_page_value)
      );

      // if user clicks popular in the list preference, launch the popular url
        if(sharedPref.getString(
                getString(R.string.pref_movie_sort_key),
                getString(R.string.pref_popular_value)
        ).equals("popular")){ // value is : popular
            url = "https://api.themoviedb.org/3/movie/popular?api_key=c20c1695d76d341c16a929a587a97dfb";
        }else if ( // if the user chooses the top rated in the list preference, launch the top rate movies url
            sharedPref.getString(
                    getString(R.string.pref_toprated_key),
                    getString(R.string.pref_toprated_value)
            ).equals("top_rated")){ // value is top_rated as seen in the query
            url = "https://api.themoviedb.org/3/movie/top_rated?api_key=c20c1695d76d341c16a929a587a97dfb";
        }else if ( // if the user chooses the upcoming in the list preference, launch the upcoming movies url
                sharedPref.getString(
                        getString(R.string.pref_upcoming_key),
                        getString(R.string.pref_upcoming_value)
                ).equals("upcoming")){
            url = "https://api.themoviedb.org/3/movie/upcoming?api_key=c20c1695d76d341c16a929a587a97dfb";
        }



        // based on the EditTextPreference as keyed in by the user, append the query page to the url
        // url with page appended is for example:
        // "https://api.themoviedb.org/3/movie/upcoming?api_key=c20c1695d76d341c16a929a587a97dfb&page=1"
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("page", moviePage);
        String url2 = uriBuilder.toString()+"";

        /// return the new url which has the page parameter appended to it
        return new MoviesLoader(this, url2);


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
        // inflate the settings menu
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    // This method is called when you select an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // Intent to launch teh settings activity
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
