package com.davidju.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public Recipe() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readList(ingredients, );
    }

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
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

}