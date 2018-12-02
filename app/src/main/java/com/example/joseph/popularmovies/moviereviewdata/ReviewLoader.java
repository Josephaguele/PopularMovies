package com.example.joseph.popularmovies.moviereviewdata;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.example.joseph.popularmovies.trailerdata.QueryDetailActivity;

import java.util.List;
public class ReviewLoader extends AsyncTaskLoader<List<Reviews>> {

    private String mUrl;

    public ReviewLoader(Context context, String url) {

        super(context);
        mUrl = url;
    }

    @Override
    public List<Reviews> loadInBackground() {
        if(mUrl == null){
            return null;
        } else{
          List<Reviews> r =  QueryDetailActivity.fetchReviewsData(mUrl);
            return r;
        }

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
