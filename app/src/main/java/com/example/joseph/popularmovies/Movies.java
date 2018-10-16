package com.example.joseph.popularmovies;

public class Movies {

    private String movieTitle;  // This is the movie title
    private int movieImage;     // Thumbnail or movie poster image
    private String overview;    // a plot synopsis (called overview in the api)
    private String releaseDate; // the release date of the movie
    private String userRating;  // called vote_average in the api


    // CONSTRUCTOR calling all movie attributes
    /*public Movies (String title, int mImage, String overview, String date, String rating){

        movieTitle = title;
        movieImage = mImage;
        this.overview =overview;
        releaseDate = date;
        userRating = rating;
    }*/

    public Movies(int Image){
        movieImage = Image;
    }

    // getters which will be called later on
   public String getMovieTitle(){ return movieTitle;}
   public String getOverview(){ return overview;}
   public int getMovieImage(){return movieImage;}
   public String getReleaseDate(){return releaseDate;}
   public String getUserRating(){return userRating;}
}
