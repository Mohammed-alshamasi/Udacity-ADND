package com.example.movie_app.Utils;

import com.example.movie_app.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;



public class JsonUtils {

    public static ArrayList<Movie> getMovieList(String json) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject jo=new JSONObject(json);


            if (jo.has("results")){

                JSONArray jsonArray = jo.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String release_date = jsonObject.getString("release_date");
                    String plot = jsonObject.getString("overview");
                    String title = jsonObject.getString("title");
                    String poster = jsonObject.getString("poster_path");
                    double rating = jsonObject.getDouble("vote_average");
                    if (!poster.equals(null)||!poster.equals("null")) {
                        Movie movie = new Movie(release_date, plot, title, poster, rating);
                        movies.add(movie);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}


