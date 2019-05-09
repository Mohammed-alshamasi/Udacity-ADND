package com.example.movie_app.Database;



import android.arch.persistence.room.Dao;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.movie_app.model.Movie;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface  MoviesDAO {
    @Insert
    public void insertMovie(Movie movie);

    @Insert
    public void insertMultipleMovies(ArrayList<Movie> movies);

    @Query("DELETE FROM MovieDB WHERE image = :id")
    void deleteMovie(String id);

    @Update
    public void updateMovie(Movie movie);

    @Query("Select * from MovieDB")
    public List<Movie> getAllMovies();

    @Query("Select * from MovieDB where image = :id")
    public Movie getSingleMovie (String id);
}
