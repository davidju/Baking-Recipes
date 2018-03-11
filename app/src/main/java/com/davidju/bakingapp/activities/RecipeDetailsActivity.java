package com.davidju.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.adapters.RecipeDetailsAdapter;
import com.davidju.bakingapp.fragments.RecipeDetailsFragment;
import com.davidju.bakingapp.fragments.RecipeStepFragment;
import com.davidju.bakingapp.interfaces.OnStepClickedListener;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.models.Step;

public class RecipeDetailsActivity extends FragmentActivity
        implements OnStepClickedListener {

    Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipe = getIntent().getParcelableExtra("recipe");
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);

        final boolean twoPane = findViewById(R.id.frame_layout) == null;
        bundle.putBoolean("two_pane", twoPane);

        if (savedInstanceState == null) {

            if (!twoPane) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                fragment.setArguments(bundle);
                transaction.add(R.id.frame_layout, fragment);
                transaction.commit();
            } else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                fragment.setArguments(bundle);
                transaction.add(R.id.master_list, fragment);
                transaction.commit();
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        Step step = recipe.getSteps().get(position - RecipeDetailsAdapter.buffer);
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.detail_list, fragment);
        transaction.commit();
    }
}
