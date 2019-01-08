package com.example.joseph.popularmovies.Models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.joseph.popularmovies.database.AppDatabase;

public class MoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppDatabase movieDatabase;

    public MoviesViewModelFactory(AppDatabase mDb){
        movieDatabase = mDb;
    }

    public <T extends ViewModel> T create (Class<T> modelClass){
        return (T) new AllMoviesViewModel(movieDatabase);
    }
}
