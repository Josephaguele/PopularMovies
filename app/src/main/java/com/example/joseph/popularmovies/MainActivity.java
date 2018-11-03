package com.example.joseph.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;


public class MainActivity extends AppCompatActivity {

    private  TheAdapter adapter;
    private static String QUERY_URL ="https://api.themoviedb.org/3/movie/popular?api_key=c20c1695d76d341c16a929a587a97dfb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_activity); // setContent to the grid view layout

//        PreferenceManager.setDefaultValues(this,R.xml.settings_main,false);

        // Get the list of movies from QueryUtils
         GridView gridView = (GridView) findViewById(R.id.listview);

        // create a new adapter that takes in a list of earthquakes
         adapter = new TheAdapter(this, new ArrayList<Movies>());

         // set the adapter on the GridView so the list can be populated in the user interface
        gridView.setAdapter(adapter);

        // this onclick method opens any movie clicked to launch the detail of that movie.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // calling intent to launch detail activity
                Movies currentMoviePoster = adapter.getItem(position);
                Movies movies = new Movies();
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("Movies", currentMoviePoster);
                startActivity(intent);


            }
        });

        MoviesAsyncTask task = new MoviesAsyncTask();
        task.execute(QUERY_URL);
    }



    private class MoviesAsyncTask extends AsyncTask<String, Void,List<Movies>> {
        @Override
        protected List<Movies> doInBackground(String...urls){
            if (urls.length < 1 || urls[0] == null){
                return null;
            }
            List<Movies>result = QueryUtils.fetchMoviesData(urls[0]);

            return result;
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {
            adapter.clear();

            if(movies != null && !movies.isEmpty()) {
                adapter.addAll(movies);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater() ;
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    // This method is called when you select an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent startSettingsActivity = new Intent(this,SettingsActivity.class);
            startActivity(startSettingsActivity);        }
        return true;
    }

}
