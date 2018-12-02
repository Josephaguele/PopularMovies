package com.example.joseph.popularmovies.moviereviewdata;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable {

    private String movieId;
    private String author; // name of reviewer
    private String content; // statement issued by the reviewer

    protected Reviews(Parcel in) {
        movieId = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    String getContent(){return content;}
    String getAuthor(){return author;}
    String getMovieId(){return movieId;}

    public Reviews(String theAuthor, String theContent, String theMovieId){
        author = theAuthor;
        content = theContent;
        movieId = theMovieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(author);
        dest.writeString(content);
    }
}
