package com.davidju.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.activities.RecipeDetailsActivity;
import com.davidju.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeSelectionAdapter extends RecyclerView.Adapter<RecipeSelectionAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipeSelectionAdapter() {
        recipes = new ArrayList<>();
    }

    public void updateRecipes(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder viewHolder, int position) {
        final Recipe recipe = recipes.get(position);
        viewHolder.recipeName.setText(recipe.getName());
        viewHolder.recipeName.setOnClickListener((View view) -> {
            Context context = viewHolder.recipeName.getContext();
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra("recipe", recipe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeName;

        RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
