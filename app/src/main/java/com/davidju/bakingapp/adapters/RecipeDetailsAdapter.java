package com.davidju.bakingapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.models.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> ingredients;
    private List<Step> steps;

    public RecipeDetailsAdapter(Recipe recipe) {
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
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
            StringBuilder builder = new StringBuilder();
            for (Ingredient ingredient : ingredients) {
                String str = "- " + ingredient.getQuantity() + " " + ingredient.getMeasure()
                        + " " + ingredient.getIngredient() + "\n";
                builder.append(str);
            }
            ((IngredientsViewHolder) viewHolder).ingredients.setText(builder.toString().trim());
        } else {
            Step step = steps.get(position - 1);
            ((StepViewHolder) viewHolder).step.setText(position + ". " + step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        // All ingredients are displayed in one item
        return steps.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
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
