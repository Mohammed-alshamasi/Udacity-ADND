package com.example.movie_app.Utils;

import android.util.Log;

import com.example.movie_app.model.Movie;
import com.example.movie_app.model.Reviews;
import com.example.movie_app.model.Trailers;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static List<Movie> getMovieList(String json) {
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject jo=new JSONObject(json);

            if (jo.has("results")){

                JSONArray jsonArray = jo.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id=jsonObject.getInt("id");
                    String release_date = jsonObject.getString("release_date");
                    String plot = jsonObject.getString("overview");
                    String title = jsonObject.getString("title");
                    String poster = jsonObject.getString("poster_path");
                    double rating = jsonObject.getDouble("vote_average");
                    if (!poster.equals(null)||!poster.equals("null")) {
                        Movie movie = new Movie(id,release_date, plot, title, poster, rating);
                        movies.add(movie);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
    public static ArrayList<Trailers> getTrailersList(String json){
        ArrayList<Trailers> trailers=new ArrayList<>();

        try {
            JSONObject jo=new JSONObject(json);

            if (jo.has("results")){
                JSONArray jsonArray =jo.getJSONArray("results");
                for (int i=0;i<=1;i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String key=jsonObject.getString("key");
                    Trailers t = new Trailers(key);
                    trailers.add(t);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return trailers;
    }
    public static ArrayList<Reviews> getReviewsList(String json){
        ArrayList<Reviews> ReviewsList=new ArrayList<>();

        try {
            JSONObject jo=new JSONObject(json);

            if (jo.has("results")){
                JSONArray jsonArray =jo.getJSONArray("results");
                for (int i=0;i<=1;i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String author=jsonObject.getString("author");
                    String content=jsonObject.getString("content");
                    Reviews r = new Reviews(author,content);
                    ReviewsList.add(r);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return ReviewsList;
    }
}


