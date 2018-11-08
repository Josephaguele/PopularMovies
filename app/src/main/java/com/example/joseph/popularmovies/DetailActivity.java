package com.example.joseph.popularmovies;

import android.content.Intent;
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

        TextView movieName = findViewById(R.id.moviename);
        TextView movieYear = findViewById(R.id.movieyear);
        TextView movieOverview = findViewById(R.id.overview);
        ImageView movieImage = findViewById(R.id.movieimage);
        TextView voteAverage = findViewById(R.id.movierating);

        // collect all our intent

        Movies movies = getIntent().getParcelableExtra("Movies");

        if(movies != null){
            movieName.setText(movies.getMovieTitle());
            movieOverview.setText(movies.getOverview());
            Picasso.with(getApplicationContext()).load(movies.getMovieImage()).into(movieImage);
            movieYear.setText(movies.getReleaseDate());
            voteAverage.setText(movies.getUserRating());


        }else{
            movieName.setText("");
        };



    }
}
