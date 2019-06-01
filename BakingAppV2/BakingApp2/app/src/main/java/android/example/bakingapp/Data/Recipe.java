package android.example.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredients> Ingredients;

    @SerializedName("steps")
    private ArrayList<Steps> Steps;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return Ingredients;
    }

    public void setIngredients (ArrayList<Ingredients> ingredients) {
        Ingredients = ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return Steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        Steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeList(this.Ingredients);
        dest.writeTypedList(this.Steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
        dest.writeInt(this.id);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.name = in.readString();
        this.Ingredients = new ArrayList<Ingredients>();
        in.readList(this.Ingredients, android.example.bakingapp.Data.Ingredients.class.getClassLoader());
        this.Steps = in.createTypedArrayList(android.example.bakingapp.Data.Steps.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
