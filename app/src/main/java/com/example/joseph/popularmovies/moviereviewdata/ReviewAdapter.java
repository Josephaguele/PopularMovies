package com.example.joseph.popularmovies.moviereviewdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.joseph.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Reviews> {

    public ReviewAdapter(Context context, List<Reviews> resource) {
        super(context, 0,resource);
    }

   public View getView(int position, View convertView, ViewGroup parent){
        View listView = convertView;
        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.review_activity,parent,false);
        }

       Reviews currentMovieUnderReview = getItem(position);


       TextView authorTextView = (TextView)listView.findViewById(R.id.reviewer_name);
        authorTextView.setText(currentMovieUnderReview.getAuthor());

        TextView contentTextView = (TextView)listView.findViewById(R.id.reviewer_summary);
        contentTextView.setText(currentMovieUnderReview.getContent());

        return listView;
   }


   public void setReviews(ArrayList<Reviews> movieReviews){

   }
}
