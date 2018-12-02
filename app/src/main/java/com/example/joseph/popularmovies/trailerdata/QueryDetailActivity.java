package com.example.joseph.popularmovies.trailerdata;

import android.text.TextUtils;
import android.util.Log;

import com.example.joseph.popularmovies.moviereviewdata.Reviews;

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

public class QueryDetailActivity {

    private static final String LOG_CHECK = QueryDetailActivity.class.getSimpleName();
    public static String key = "";

    // calling Constructor
    private QueryDetailActivity() {
    }

    public static List<Trailer> fetchVideosData(String requestUrl) {
        Log.i(LOG_CHECK, "Creating url object to fetch the movie trailer data");
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_CHECK, "Problem making the HTTP request", e);
        }

        List<Trailer> videos = extractTrailersfromJsonResponse(jsonResponse);
        return videos;
    }

    // this is calling the fetch Movies data
    public static List<Reviews> fetchReviewsData(String requestUrl){
        Log.i(LOG_CHECK, "Creating url object to fetch the movie reviews data");
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_CHECK, "Problem making HTTP request", e);
        }

        List<Reviews> reviewsList = extractReviewsfromJsonResponse(jsonResponse);
        return reviewsList;
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
                Log.e(LOG_CHECK, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_CHECK, "Problem retrieving the moviesdb api JSON results.", e);
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
        }
        return jsonResponse;
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


    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_CHECK, "Problem building the URL", e);
        }
        return url;
    }

    // extracting Trailers from Json response
    private static List<Trailer> extractTrailersfromJsonResponse(String videosJSON) {

        // if Json String is empty, return early
        if (TextUtils.isEmpty(videosJSON)) {
            return null;
        }

        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(videosJSON);
            String videoId = root.optString("id");
            JSONArray results = root.optJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject firstVideo = results.optJSONObject(i);
                String vId = firstVideo.optString("id");
                String key = firstVideo.optString("key");
                String name = firstVideo.optString("name");
                String site = firstVideo.optString("site");
                String size = firstVideo.optString("size");


                Trailer movieTrailer = new Trailer(vId, key, name, site, size);
                trailers.add(movieTrailer);
            }

        } catch (JSONException e) {
            Log.e("Query utils", "problem parsing the videos JSON results", e);
        }
        return trailers;
    }

    // extracting Reviews from Json response
    private static List<Reviews> extractReviewsfromJsonResponse(String reviewsJson){
        if (TextUtils.isEmpty(reviewsJson)){
            return null;
        }

        List<Reviews> movieReviews = new ArrayList<>();
        try{
            JSONObject ROOT = new JSONObject(reviewsJson);
            int movieId = ROOT.optInt("id");
            JSONArray results = ROOT.optJSONArray("results");
            for(int i = 0; i <results.length(); i++){
                JSONObject firstReview = results.optJSONObject(i);
                String author = firstReview.optString("author");
                String content = firstReview.optString("content");

                // converting movieId to String
                String moId = Integer.toString(movieId);
                Reviews reviews = new Reviews(author, content, moId);
                movieReviews.add(reviews);
            }
        } catch (JSONException e) {
            Log.e("Query utils", "there is a problem parsing the reviews JSON results, kindly check properly", e);
        }
        return movieReviews;
    }
}
