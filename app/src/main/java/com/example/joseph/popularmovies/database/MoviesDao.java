package com.example.joseph.popularmovies;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM allmovies ORDER BY userRating")
    ArrayList<Movies> loadAllMovies();

    @Insert
    void insertMovies(Movies movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(Movies movies);

    @Delete
    void deleteMovies(Movies movies);

}
