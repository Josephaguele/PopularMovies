package com.example.joseph.popularmovies.moviereviewdata;

import android.app.ActionBar;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.joseph.popularmovies.Movies;
import com.example.joseph.popularmovies.R;
import com.example.joseph.popularmovies.trailerdata.QueryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.joseph.popularmovies.DetailActivity.API_KEY;

public class ReviewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Reviews>>{

private static String QUERY_URL = "https://api.themoviedb.org/3/movie/184/reviews?api_key=c20c1695d76d341c16a929a587a97dfb";
    ReviewAdapter nAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviewlist);

        ListView listView = (ListView)findViewById(R.id.listview2);

         nAdapter = new ReviewAdapter(this,  new ArrayList<Reviews>());
        listView.setAdapter(nAdapter);

        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();


    }



    @Override
    public Loader<List<Reviews>> onCreateLoader(int id, Bundle args) {

        // Parcelable helps to pass a json data from one activity to the other
        // Get the id of the movie clicked
        String currentMovieId = getIntent().getStringExtra("Movies");

        String url = "https://api.themoviedb.org/3/movie/";

        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(currentMovieId) // append the id to the url so you
                .appendPath("reviews")
                .appendQueryParameter("api_key", API_KEY)
                .build();

        Log.i("Movie id here is:", currentMovieId);

        String url2 = uriBuilder.toString()+"";
        return new ReviewLoader (this, url2);
    }

    @Override
    public void onLoadFinished(Loader<List<Reviews>> loader, List<Reviews> data) {

        // Update the UI with the result
        // sames as onPostExecute method of the AsyncTask

        // clear the adapter of previous data
        nAdapter.clear();

        // if the movie list is valid, then add to the list and update it
        if (data != null && !data.isEmpty()) {
            nAdapter.addAll(data);
        }else if(data == null && data.isEmpty()){
            Toast.makeText(getApplicationContext(),"No reviews for this movie",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Reviews>> loader) {
        // Loader reset, so we can clear out our existing data
        nAdapter.setReviews(new ArrayList<Reviews>());
        nAdapter.clear();
    }
}


