package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONObject foodData = jsonObject.getJSONObject("name");

            String mainName = foodData.getString("mainName");

            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            String description = jsonObject.getString("description");

            String image = jsonObject.getString("image");

            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJsonArray = foodData.getJSONArray("alsoKnownAs");

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");

            for (int m = 0; m < alsoKnownAsJsonArray.length(); m++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(m));
            }

            for (int f = 0; f < ingredientsJsonArray.length(); f++) {
                ingredients.add(ingredientsJsonArray.getString(f));
            }

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
