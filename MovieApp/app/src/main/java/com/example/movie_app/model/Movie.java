package com.example.movie_app.model;

public class Movie {

    private String plot,title,image,release_date;
    private double rating;


    public Movie(String release_date, String plot, String title, String image, double rating) {
        this.release_date = release_date;
        this.plot = plot;
        this.title = title;
        this.image = image;
        this.rating = rating;
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
        return buildFullPosterPath();
    }

    public double getRating() {
        return rating;
    }

    private String buildFullPosterPath() {
        String firstPartOfUrl = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";
        return firstPartOfUrl + image;
    }
}
