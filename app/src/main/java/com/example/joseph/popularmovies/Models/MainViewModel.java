package com.example.joseph.popularmovies.Models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.joseph.popularmovies.Movies;
import com.example.joseph.popularmovies.database.AppDatabase;

import java.util.List;

public class AllMoviesViewModel extends ViewModel {
    // CONSTANT FOR LOGGING
    private static final String TAG = AllMoviesViewModel.class.getSimpleName();

    private LiveData<List<Movies>> movies;

    public AllMoviesViewModel(AppDatabase mDb) {
        // querying the database for the whole list of favourite movies
        movies = mDb.moviesDao().loadAllMovies();

    }

    public LiveData<List<Movies>> getAllMovies() {
        return movies;
    }
}
