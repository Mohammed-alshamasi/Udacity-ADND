package android.example.bakingapp.Network;

import android.example.bakingapp.Data.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
        @GET("baking.json")
        Call<ArrayList<Recipe>> getRecipes();
    }

