package com.example.joseph.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TheAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridlist); // setContent to the grid view layout

       Movies[] movies = {
               new Movies(R.drawable.sample_5),
               new Movies(R.drawable.sample_0),
               new Movies(R.drawable.sample_1),
               new Movies(R.drawable.sample_4),
               new Movies(R.drawable.sample_2),
               new Movies(R.drawable.sample_3)
       };


         adapter = new TheAdapter(this, Arrays.asList(movies));


        final GridView gridView = (GridView) findViewById(R.id.listview);
        gridView.setAdapter(adapter);



    }
}
