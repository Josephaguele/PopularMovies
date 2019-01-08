package com.example.joseph.popularmovies.database;

// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.joseph.popularmovies.Movies;

@Database(entities ={Movies.class},version = 1, exportSchema = false)
public abstract class MoviesRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = MoviesRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movieslist";
    private static MoviesRoomDatabase singleInstance;

    public static MoviesRoomDatabase getInstance(Context context){
        if(singleInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new databse instance");
                singleInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesRoomDatabase.class, MoviesRoomDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return singleInstance;
    }

    public abstract MoviesDao moviesDao();
}
