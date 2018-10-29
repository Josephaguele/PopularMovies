package com.example.joseph.popularmovies;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOSEPH 16/10/2018.
 */


public final class QueryUtils {

     static String TITLE = "title";
     static String  POSTERPATH = "poster_path";
     static String OVERVIEW = "overview";
     static String RELEASE_DATE = "releaseDate";

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    final static String baseURL = "http://image.tmdb.org/t/p/w185";
    private QueryUtils(){}

    /**
     * Query the website dataset and return a list of {@link Movies} objects.
     */
    public static List<Movies> fetchMoviesData(String requestUrl) {
        // Create URL object

        Log.i(LOG_TAG, "Creating URL Object to fetch movies data");
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link movies}
        List<Movies> movies = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Movies}s
        return movies;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the moviesdb api JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        } return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Movies> extractFeatureFromJson(String moviesJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movie thumbs to
        List<Movies> movies = new ArrayList<>();


        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create a JSONObject from the JSON response string
            JSONObject root = new JSONObject(moviesJSON);
            JSONArray results = root.optJSONArray("results");
            for( int i = 0;  i < results.length(); i++){
                JSONObject firstStart = results.optJSONObject(i);
                int voteAverage = firstStart.optInt("vote_average"); // Movie rating
                String title = firstStart.optString("title"); // The title of the movie
                String poster_path = firstStart.optString(POSTERPATH); // image
                String overview = firstStart.optString(OVERVIEW); // The story line of the movie
                String releaseDate = firstStart.optString("release_date"); // to generate the release date
                String backdropPath = firstStart.optString("backdrop_path");


                // Create a new {@link Movies} object with the title, overview, release date, rating
                // from the JSON response.
                // Movies newMovies = new Movies(title, poster,overview,releaseDate,voteAverage);
               // Movies mainPageMovies = new Movies(poster_path);

                // The sting imagePath is the path of the image poster for the movie
                // which is also continued by the poster_path which is derived from the JSON parsing.
                // a full image poster path will look like http://image.tmdb.org/t/p/w500/8u00gUM8aNqYLs10sTBQiXu0fEv.jpg
                String imagePath =  "http://image.tmdb.org/t/p/w342/";
                String imagePoster = imagePath +  poster_path;

                String backDrop =  "http://image.tmdb.org/t/p/w500/";
                String detailsImage = backDrop + backdropPath;

                // since the result of vote_average is in integers, this converts voteAverage back to a String resource
               String votes= Integer.toString(voteAverage);
               String movieRating = votes + " / 10" ;

                //Movies mainPageMovies = new Movies(imagePoster);
                // Add the new {@link Movies} to the list of movies
                // movies.add(newMovies);
                //movies.add(mainPageMovies);

                Movies mm = new Movies(title,imagePoster,overview,releaseDate,movieRating,detailsImage);
                movies.add(mm);

            }

            //Uri imageUri = Uri.parse(baseURL).buildUpon().

            } catch(JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the movies JSON results", e);
        }
        // Return the list of popular movies
        return movies;
    }




}
