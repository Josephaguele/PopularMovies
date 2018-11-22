package com.example.joseph.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

// Parcelable needs rthis code to function. It handles the process of copying your object data into a parcel for transmission
// between activities and then recreating the object on the other side.

public class Movies implements Parcelable {

    private String movieTitle;  // This is the movie title
    private String movieImage;     // Thumbnail or movie poster image
    private String overview;    // a plot synopsis (called overview in the api)
    private String releaseDate; // the release date of the movie
    private String userRating;  // called vote_average in the api
    private String detailsImage;

   // CONSTRUCTOR calling all movie attributes
    public Movies (String title, String mImage, String overview, String date, String rating, String mmImage){

        movieTitle = title;
        movieImage = mImage;
        this.overview =overview;
        releaseDate = date;
        userRating = rating;
        detailsImage = mmImage;
    }



   public Movies(String Image){
        movieImage = Image;
    }

    // This method is the constructor, called on the receiving activity, where you will be collecting values.
    // This constructor is where you set up the values and set up the properties of the object. At this point
    // you have populated the object with data

    protected Movies(Parcel in) {
        movieTitle = in.readString();
        movieImage = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        userRating = in.readString();
        detailsImage = in.readString();

    }

    // Parcelable requires this method to bind everything together. There's little you need to do here as the
    // create FromParcel method will return your newly populated object
    // USED WHEN un-parcelling our parcel (creating the object)
    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Movies() {

    }

    // getters which will be called later on
   public String getMovieTitle(){ return movieTitle;}
   public String getOverview(){ return overview;}
   public String getMovieImage(){return movieImage;}
   public String getReleaseDate(){return releaseDate;}
   public String getUserRating(){return userRating;}
    public String getDetailsImage(){return  detailsImage;}

    @Override
    public int describeContents() {
        return 0;
    }


    // In this method, you add all your class properties to the parcel in preparation for transfer. You use each of the
    // write methods to add each of your properties
    @Override
    // write object values to parcel for storage
    // Note: the order in which you wirte these values is important. When collecting the values later you will need to collect
    // them in the same order
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieTitle);
        dest.writeString(movieImage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(userRating);
        dest.writeString(detailsImage);
    }


}
