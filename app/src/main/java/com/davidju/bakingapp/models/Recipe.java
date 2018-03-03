package com.davidju.bakingapp.models;

import java.util.List;

public class Recipe {

    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

}