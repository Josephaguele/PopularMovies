package com.example.joseph.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joseph.popularmovies.moviereviewdata.ReviewActivity;
import com.example.joseph.popularmovies.trailerdata.QueryDetailActivity;
import com.example.joseph.popularmovies.trailerdata.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String API_KEY = "c20c1695d76d341c16a929a587a97dfb";
    // e.g https://youtube.com/watch?v=trailerKey


    // Implementation of stage 2 stuffs
    boolean isPlay = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail);

        youtubeLauncherID(); // calling method to append the movie id
        DetailsPage(); // calling detailsPage
        ReviewActivityLauncherButton();
        favouriteMovieActivation();


    }

    void favouriteMovieActivation() {
        ImageButton favouritesMovie = (ImageButton) findViewById(R.id.favouriteButton);
        // Default button, if need set it in xml background = "@drawable/btn_rating"
        favouritesMovie.setBackgroundResource(R.drawable.btn_rating);
        favouritesMovie.setOnClickListener(favouriteMoviesToggleButton);

    }
    // this toggles the star button when clicked
    View.OnClickListener favouriteMoviesToggleButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // change the button background when clicked by the user
            if(isPlay){ // if the button is not clicked, the default colour of the button is shown
                v.setBackgroundResource(R.drawable.btn_rating);
            }else{ // if the button is clicked, the default colour of the button changes to yellow.
                // which means that the user has selected the movie as a favourite movie.
                v.setBackgroundResource(R.drawable.favourite);
                Toast.makeText(getApplicationContext(),"marked as favourite",Toast.LENGTH_LONG).show();
            }isPlay = !isPlay; // reverse
        }
    };

    void DetailsPage() {
        // collect all our intent

        // locate views in the layout and link them with their corresponding variables
        TextView movieName = (TextView) findViewById(R.id.moviename);
        TextView movieYear = (TextView) findViewById(R.id.movieyear);
        TextView movieOverview = (TextView) findViewById(R.id.overview);
        ImageView movieImage = (ImageView) findViewById(R.id.movieimage);
        TextView voteAverage = (TextView) findViewById(R.id.movierating);
        // Parcelable helps to pass a json data from one activity to the other
        Movies movies = getIntent().getParcelableExtra("Movies");
        if (movies != null) {
            movieName.setText(movies.getMovieTitle());
            movieOverview.setText(movies.getOverview());
            // load the image using Picasso library
            Picasso.with(getApplicationContext()).load(movies.getMovieImage()).into(movieImage);
            movieYear.setText(movies.getReleaseDate());
            voteAverage.setText(movies.getUserRating());
        } else {
            movieName.setText("");
        }
    }

    void youtubeLauncherID() {
        // Parcelable helps to pass a json data from one activity to the other
        Movies movies = getIntent().getParcelableExtra("Movies");


        // Get the id of the movie clicked
        String currentMovieId = movies.getMovieId();

        //String c = getIntent().getStringExtra("Movies");
        String url = "https://api.themoviedb.org/3/movie/";

        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath(currentMovieId) // append the id to the url so you
                // https://api.themoviedb.org/3/movie/339253/videos?api_key="";
                .appendPath("videos")
                .appendQueryParameter("api_key", API_KEY)
                .build();
        String url2 = uriBuilder.toString() + "";


        Log.i("CURRENT MOVIE ID IS:", currentMovieId); // Adding movieId to the log just for checking movie id


        // Calling the AsyncTask method that did the background work to avoid multiple threads on the DetailsActivity.
        DownloadVideosTask task = new DownloadVideosTask();
        task.execute(url2);

    }

    void ReviewActivityLauncherButton() {

        // activating the movie reviews button to launch the Review Activity
        Button userReviews = (Button) findViewById(R.id.reviews_button);
        userReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Movies movies = getIntent().getParcelableExtra("Movies");
                // Get the id of the movie clicked
                String currentMovieId = movies.getMovieId();

                Intent reviewActivityIntent = new Intent(getApplicationContext(), ReviewActivity.class);
                //adding the id of the movie which is already declared final
                reviewActivityIntent.putExtra("Movies", currentMovieId);
                startActivity(reviewActivityIntent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void loadMovieTrailer(List<Trailer> movieTrailer) {

// IMPLEMENTING BUTTON PLAY BUTTON CLICK IN THE MOVIE DETAIL TO PLAY THE MOVIE TRAILER
        for (int indexPosition = 0; indexPosition < movieTrailer.size(); indexPosition++) {
            final Trailer trailers = movieTrailer.get(indexPosition);

            ImageButton playTrailer = (ImageButton) findViewById(R.id.play);
            playTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String trailerKey = trailers.gettrailerKey();
                    String videourl = "https://youtube.com/watch";
                    Uri launchUrl = Uri.parse(videourl);
                    Uri.Builder uriBuilder2 = launchUrl.buildUpon();
                    uriBuilder2.appendQueryParameter("v", trailerKey)
                            .build();
                    // convert url to string
                    String finalVideoUrl = uriBuilder2.toString() + "";

                    // Convert the youtubeUrl in string to type URL
                    Uri youtubeUrl = Uri.parse(finalVideoUrl);

                    // play the youtube video only after if you have checked that there is an app (browser, or youtube app) that
                    // will launch the video intent
                    Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, youtubeUrl);
                    if (playVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(playVideoIntent);
                    }
                }
            });

        }

    }


    private class DownloadVideosTask extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected List<Trailer> doInBackground(String... urls) {

            // if the urls length is null, or if the first element of the url is null, return a null value
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Trailer> result = QueryDetailActivity.fetchVideosData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Trailer> movieTrailer) {
            // normally i  would have just said loadMovieTrailer(movieTrailer), but if i implement that
            /* this app will crash if the internet is off because the trailer derives its data (movieTrailer from the internet)
            i.e if the internet is off, the movieTrailer will give a null value which will cause a crash

            The solution is to load the movieTrailer only when it is not null, that is only when the internet is on.
            So if the user tries to watch the trailer when the internet is off, it will take the user to the url
            but the trailer can't be watched, off course because the internet is off.
            * */
            if (movieTrailer != null) {
                // calling loadMovieTrailer method
                loadMovieTrailer(movieTrailer);
            }

            super.onPostExecute(movieTrailer);
        }
    }


}


