package com.davidju.bakingapp.adapters;

import android.view.View;

import com.davidju.bakingapp.interfaces.OnRecipeSelectedListener;
import com.davidju.bakingapp.models.Recipe;

public class RecipeWidgetSelectionAdapter extends RecipeSelectionAdapter {

    public OnRecipeSelectedListener callback;

    @Override
    public void onBindViewHolder(RecipeViewHolder viewHolder, int position) {
        final Recipe recipe = recipes.get(position);
        viewHolder.recipeName.setText(recipe.getName());
        viewHolder.recipeName.setOnClickListener((View view) ->
            callback.onRecipeSelected(position));
    }

    public Recipe getRecipe(int position) {
        return recipes.get(position);
    }
}
