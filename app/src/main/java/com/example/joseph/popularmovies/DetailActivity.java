package com.example.joseph.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail);

        // locate views in the layout and link them with their corresponding variables
        TextView movieName = (TextView) findViewById(R.id.moviename);
        TextView movieYear = (TextView) findViewById(R.id.movieyear);
        TextView movieOverview = (TextView) findViewById(R.id.overview);
        ImageView movieImage = (ImageView) findViewById(R.id.movieimage);
        TextView voteAverage = (TextView) findViewById(R.id.movierating);

        // collect all our intent

        Movies movies = getIntent().getParcelableExtra("Movies");

        if(movies != null){
            movieName.setText(movies.getMovieTitle());
            movieOverview.setText(movies.getOverview());
            // load the image using Picasso library
            Picasso.with(getApplicationContext()).load(movies.getMovieImage()).into(movieImage);
            movieYear.setText(movies.getReleaseDate());
            voteAverage.setText(movies.getUserRating());


        }else{
            movieName.setText("");
        }


    }
}
