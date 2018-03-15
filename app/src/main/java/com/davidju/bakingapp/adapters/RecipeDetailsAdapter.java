package com.davidju.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.interfaces.OnStepClickedListener;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for RecyclerView that displays details for a selected recipe.
 */
public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int buffer = 2;
    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    public OnStepClickedListener callback;

    public RecipeDetailsAdapter(Recipe recipe) {
        name = recipe.getName();
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
            return new RecipeViewHolder(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredients, parent, false);
            return new IngredientsViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
            return new StepViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == 0) {
            ((RecipeViewHolder) viewHolder).recipeName.setText(name);
        } else if (viewHolder.getItemViewType() == 1) {
            StringBuilder builder = new StringBuilder();
            for (Ingredient ingredient : ingredients) {
                String str = "- " + ingredient.getQuantity() + " " + ingredient.getMeasure()
                        + " " + ingredient.getIngredient() + "\n";
                builder.append(str);
            }
            ((IngredientsViewHolder) viewHolder).ingredients.setText(builder.toString().trim());
        } else {
            Step step = steps.get(position - buffer);
            String str = step.getShortDescription();
            if (position > buffer) {
                str = (position - buffer) + ". " + str;
            }
            ((StepViewHolder) viewHolder).step.setText(str);
            ((StepViewHolder) viewHolder).step.setOnClickListener((View v) ->
                    callback.onStepSelected(position));
        }
    }

    @Override
    public int getItemCount() {
        // All ingredients are displayed in one item
        return steps.size() + buffer;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) { // Recipe name
            return 0;
        } else if (position == 1) { // Recipe ingredients
            return 1;
        } else { // Recipe steps
            return 2;
        }
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name) TextView recipeName;
        RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients) TextView ingredients;
        IngredientsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step) TextView step;
        StepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
