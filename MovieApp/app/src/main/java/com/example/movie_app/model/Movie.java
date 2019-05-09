package com.example.movie_app.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "MovieDB")
public class Movie {

    @PrimaryKey
    private int movieID;
    private String plot,title,image,release_date,moviePoster;
    private double rating;


    public Movie(int movieID,String release_date, String plot, String title, String image, double rating) {
        this.movieID=movieID;
        this.release_date = release_date;
        this.plot = plot;
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.moviePoster=buildFullPosterPath();
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPlot() {
        return plot;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    private String buildFullPosterPath() {
        String firstPartOfUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
        return firstPartOfUrl + image;
    }
}
