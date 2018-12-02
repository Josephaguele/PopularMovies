package com.example.joseph.popularmovies.trailerdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.joseph.popularmovies.Movies;
import com.example.joseph.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

// passing it Trailer as the Parameter for the ArrayAdapter just like we pass in Strings
public class TrailerAdapter extends ArrayAdapter<Trailer> {


    public TrailerAdapter( Context context, List<Trailer> trailers) {

        super(context, 0,trailers);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // Gets the  object from the ArrayAdapter at the appropriate position.
        View gridView = convertView;
        if(gridView == null){
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.mainlayout,parent,false);
        }


        Trailer videoTrail = getItem(position);


        //Adapters recycle views to Adapter views.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView.
        // and we modify the View widgets as usual.

        return  gridView;
    }
}
