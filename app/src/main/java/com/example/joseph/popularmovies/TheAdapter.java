package com.example.joseph.popularmovies;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Here i have passed in the movies class which is the main parameter for the array of images needed
public class TheAdapter extends ArrayAdapter<Movies> {


    /*This is our own custom constructor (it doesn't mirror a superclass constructor
    * The context is used to inflate the layout file, and teh list is the data we want to populate
    * into the lists*/

    public TheAdapter(Context context, List<Movies> thumbnails) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // The second argument is used when the ArrayAdapter is populating a single TextView.
        // because this is is a custom adapter for only one ImageView, the adapter is not
        // going to use this second argument so it can be any value. Here we used 0
        super(context, 0, thumbnails);
    }

    /*
    * Provides a view for an AdapterView (ListView, GridView) etc
    *
    * @param position       The AdapterView position that is requesting a view
    * @param convertView    The recycled view to populate
    * @param parent The parent ViewGroup that is used for inflation
    * @return   The View for the position in the AdapterView.
     * */

    public View getView(int position, View convertView, ViewGroup parent){
        // Gets the  object from the ArrayAdapter at the appropriate position.
        View gridView = convertView;
        if(gridView == null){
            gridView = LayoutInflater.from(getContext()).inflate(R.layout.mainlayout,parent,false);
        }


        Movies thumbs = getItem(position);


        //Adapters recycle views to Adapterviews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated froma previous call to getView.
        // and we modify the View widgets as usual.


        ImageView imageView = gridView.findViewById(R.id.thumbImage);
        //imageView.setImageResource(thumbs.getMovieImage());
        Picasso.with(getContext()).load(thumbs.getMovieImage()).into(imageView);


        return  gridView;
    }

    public void setMovies(ArrayList<Movies> movies){

    }
}
